package com.terminplaner.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.terminplaner.entity.Appointment;
import com.terminplaner.entity.Customer;
import com.terminplaner.entity.Department;
import com.terminplaner.service.AppointmentService;
import com.terminplaner.service.CustomerService;
import com.terminplaner.service.NotFoundException;

@Controller
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	private CustomerService custService;
		
	@GetMapping("/appointments/new/{date}")
	public String newAppointment(@PathVariable(name="date") Long millisec, Model model) {
		
		Date date = new Date(millisec);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = simpleDateFormat.format(date);
		
		
		List<Department> departments = appointmentService.listDepartments();
		
		Appointment appointment = new Appointment();
		
		model.addAttribute("date", dateString);
		model.addAttribute("listDepartments", departments);
		model.addAttribute("appointment", appointment);
		model.addAttribute("millisec", millisec);
		
		
		return "appointment_form";
	}
	
	@GetMapping("/appointments/{date}")
	public String listAppointments(@PathVariable(name="date") Long millisec, Model model) {
		Date date = new Date(millisec);
		List<Appointment> appointmentList = appointmentService.findByDate(date);
		
		model.addAttribute("appointmentList", appointmentList);
		model.addAttribute("millisec", millisec);

		return "index";
	}
	
	@GetMapping("")
	public String showFirstPage(Model model) {

		Long todaysDateInMillisec = new Date().getTime();
		
		return "redirect:/appointments/" + todaysDateInMillisec;
	}
	
	@GetMapping("/appointments/new/{date}/{cust.id}")
	public String newAppointmentForExistingCustomer(@PathVariable(name="date") Long millisec, @PathVariable(name="cust.id") Integer custId, Model model) {
		
		Date date = new Date(millisec);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = simpleDateFormat.format(date);
		
		List<Department> departments = appointmentService.listDepartments();
		Customer customer = custService.findById(custId);
		
		Appointment appointment = new Appointment();
		appointment.setCustomer(customer);
		
		model.addAttribute("date", dateString);
		model.addAttribute("listDepartments", departments);
		model.addAttribute("appointment", appointment);
		model.addAttribute("millisec", millisec);
		model.addAttribute("custId", custId);
		
		return "appointment_form";
	}
	
	@PostMapping("/appointments/save")
	public String saveAppointment(Appointment appointment, RedirectAttributes redirectAttributes) {
		
		long millisec = appointment.getDateOfVisit().getTime();
		
		custService.save(appointment.getCustomer());
		
		appointmentService.save(appointment);
		
		redirectAttributes.addFlashAttribute("message", "The appointment has been saved successfully");
		
		return "redirect:/appointments/" + millisec;
	}
	

	@GetMapping("/appointments/delete/{id}")
	public String deleteAppointment(@PathVariable(name="id") Integer id, Model model, RedirectAttributes redirectAttributes ) {
		try {
			Long millisec = appointmentService.findById(id).getDateOfVisit().getTime();
			appointmentService.delete(id);
			redirectAttributes.addFlashAttribute("message", "The appointment has been deleted successfully");
			
			return "redirect:/appointments/" + millisec;
		} catch(NotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			return "index";
		}
	}
	
	@GetMapping("/appointments/edit/{id}")
	public String editAppointment(@PathVariable(name="id") Integer id, Model model, RedirectAttributes redirectAttributes ) {
		try {
			List<Department> departments = appointmentService.listDepartments();
			Appointment appointment = appointmentService.findById(id);
			
			Date date = appointment.getDateOfVisit();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = simpleDateFormat.format(date);
			
			model.addAttribute("custId", appointment.getCustomer().getId());
			model.addAttribute("date", dateString);
			model.addAttribute("listDepartments", departments);
			model.addAttribute("appointment", appointment);
			model.addAttribute("millisec", date.getTime());

			return "appointment_form";
		} catch (NotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			return "index";
		}
	}
}
