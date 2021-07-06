package com.terminplaner.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.terminplaner.entity.Appointment;
import com.terminplaner.entity.Department;
import com.terminplaner.repository.AppointmentRepository;
import com.terminplaner.repository.DepartmentRepository;

@Service
@Transactional
public class AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepo;
	
	@Autowired
	private DepartmentRepository departmentRepo;
		
	public Appointment save(Appointment appointment) {
		return appointmentRepo.save(appointment);
	}
	
	public List<Department> listDepartments(){
		return (List<Department>)departmentRepo.findAll();
	}
	
	public List<Appointment> findByDate(Date date){
		return appointmentRepo.findByDate(date);
	}
	
	public Appointment findById(Integer id) throws NotFoundException{
		if (appointmentRepo.existsById(id)) {
			return appointmentRepo.findById(id).get();
		} else {
			throw new NotFoundException("Appointment not found");
		}	
	}
	
	public void delete(Integer id) throws NotFoundException {
		if (appointmentRepo.existsById(id)) {
			appointmentRepo.deleteById(id);
		} else {
			throw new NotFoundException("Appointment not found");
		}
	}
	
	public String checkIfAppointmentIsOK (Appointment appointment) {
		boolean customerIsAvailable = checkCustomerAvailability(appointment);
		boolean departmentHasCapacity = checkIfDepartmentHasCapacity(appointment);
	
		if (!departmentHasCapacity && !customerIsAvailable) {
			return "BothError";
		} else if (!customerIsAvailable) {
			return "CustError";
		} else if (!departmentHasCapacity) {
			return "DeptError";
		} else {
			return"OK";
		}
	}
	
	private boolean checkCustomerAvailability(Appointment appointmentToCheck) {
		Date dateToCheckStart = appointmentToCheck.getDateOfVisit();
		Date dateToCheckEnd = appointmentToCheck.getEndDate();
		
		List<Appointment> appointmentsToday = appointmentRepo.findByDate(appointmentToCheck.getDateOfVisit());
		
		List<Appointment> custAppointmentsToday = appointmentsToday.stream()
				.filter(a -> a.getCustomer().equals(appointmentToCheck.getCustomer()))
				.collect(Collectors.toList());
		
		for (Appointment appointment : custAppointmentsToday) {	
			Date appointmentStart = new Date(appointment.getDateOfVisit().getTime());
			Date appointmentEnd = new Date(appointment.getEndDate().getTime());
			
			
			if(appointmentStart.before(dateToCheckStart) 
					&& appointmentEnd.after(dateToCheckStart)) {
				return false;
			}
			
			if(dateToCheckStart.before(appointmentStart) 
					&& dateToCheckEnd.after(appointmentStart)) {
				return false;
			}
			
			if(appointmentStart.equals(dateToCheckStart)) {
				return false;
			}
		}		
		return true;
	}
	
	private boolean checkIfDepartmentHasCapacity(Appointment appointmentToCheck) {
		Date dateToCheckStart = appointmentToCheck.getDateOfVisit();
		Date dateToCheckEnd = appointmentToCheck.getEndDate();
		
		List<Appointment> appointmentsToday = appointmentRepo.findByDate(appointmentToCheck.getDateOfVisit());
		Department department = appointmentToCheck.getDepartment();
		
		int customersPresent = 0;
		
		List<Appointment> deptAppointmentsToday = appointmentsToday.stream()
				.filter(a -> a.getDepartment().equals(department))
				.collect(Collectors.toList());
		
		for (Appointment appointment : deptAppointmentsToday) {
			Date appointmentStart = new Date(appointment.getDateOfVisit().getTime());
			Date appointmentEnd = new Date(appointment.getEndDate().getTime());
			
			if(appointmentStart.equals(dateToCheckStart)) {
				customersPresent++;
			} else if (appointmentStart.after(dateToCheckStart) && appointmentStart.before(dateToCheckEnd)) {
				customersPresent++;
			} else if (appointmentStart.before(dateToCheckStart) && appointmentEnd.after(dateToCheckStart)) {
				customersPresent++;
			} 
		}
		return customersPresent + 1 <= department.getMaxCustomer();
	}

	public Department findDepartmentById(Integer deptId) {
		List<Department> listDepartments = listDepartments();
		
		Department department = listDepartments.stream()
				.filter(d -> d.getId() == deptId)
				.findAny()
					.orElse(null);
		
		return department;
	}
	
	
}
