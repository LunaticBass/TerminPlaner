package com.terminplaner.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.terminplaner.entity.Appointment;
import com.terminplaner.entity.Customer;
import com.terminplaner.entity.Department;
import com.terminplaner.service.AppointmentService;
import com.terminplaner.service.CustomerService;


@RestController
public class AppointmentRestController {

	@Autowired
	private CustomerService custService;
	
	@Autowired
	private AppointmentService appService;
	
	@PostMapping("/customers/check_email")
	public String checkDuplicateEmail(@Param("id") Integer id, @Param("email") String email) {
				
		return custService.isEmailUnique(id, email) ? "OK" : "Duplicated";
	}
	
	@PostMapping("/appointments/check_appointment")
	public String checkAppointment(@Param("custId") Integer custId, @Param("deptId") Integer deptId, 
			@Param("millisec") Long millisec, @Param("purpose") String purpose) {
		
		Customer customer;
		if (custId == null) {
			customer = null;
		} else {
			customer = custService.findById(custId);
		}
		
		Department department = appService.findDepartmentById(deptId);		
		Date date = new Date(Long.valueOf(millisec));
		
		Appointment appointment = new Appointment();
		appointment.setDateOfVisit(date);
		appointment.setPurposeOfVisit(purpose);
		appointment.setCustomer(customer);
		appointment.setDepartment(department);

		return appService.checkIfAppointmentIsOK(appointment);
	}
}
