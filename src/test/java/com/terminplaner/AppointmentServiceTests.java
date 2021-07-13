package com.terminplaner;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.terminplaner.entity.Appointment;
import com.terminplaner.entity.Customer;
import com.terminplaner.entity.Department;
import com.terminplaner.repository.AppointmentRepository;
import com.terminplaner.service.AppointmentService;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class AppointmentServiceTests {
	private Appointment appointment;
	private Customer customer;
	
	
	@BeforeEach
	public void initializeAppointments() {
		LocalDateTime dateTime = LocalDateTime.of(2021, 2, 12, 10, 30);
		Date date = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
		Department department1 = new Department();
		department1.setMaxCustomer(5);
		customer = new Customer();
		
		appointment = new Appointment();
		appointment.setDateOfVisit(date);
		appointment.setDepartment(department1);
		appointment.setCustomer(customer);
		appointment.setPurposeOfVisit("Testing");
	}
	
	@MockBean
	public AppointmentRepository repo;
	
	@InjectMocks
	public AppointmentService service;
	
	@Test
	public void testCheckIfAppointmentIsOK_CustomerDateCollision() {
		Date date = appointment.getDateOfVisit();
		
		LocalDateTime dateTimeToReturn = LocalDateTime.of(2021, 2, 12, 11, 00);
		Date dateToReturn = Date.from(dateTimeToReturn.atZone(ZoneId.systemDefault()).toInstant());
		
		Department department2 = new Department();
		
		Appointment appointmentToReturn = new Appointment();
		appointmentToReturn.setCustomer(customer);
		appointmentToReturn.setDepartment(department2);
		appointmentToReturn.setDateOfVisit(dateToReturn);
		appointmentToReturn.setPurposeOfVisit("Click & Collect");
		
		Mockito.when(repo.findByDate(date)).thenReturn(List.of(appointmentToReturn));
		
		String check = service.checkIfAppointmentIsOK(appointment);
		assertThat(check).isEqualTo("CustError");
	}
	
	@Test
	public void testCheckIfAppointmentIsOK_CustomerDateOK() {
		Date date = appointment.getDateOfVisit();
		
		LocalDateTime dateTimeToReturn = LocalDateTime.of(2021, 2, 12, 8, 30);
		Date dateToReturn = Date.from(dateTimeToReturn.atZone(ZoneId.systemDefault()).toInstant());
		
		Department department2 = new Department();
		department2.setMaxCustomer(5);
		
		Appointment appointmentToReturn = new Appointment();
		appointmentToReturn.setCustomer(customer);
		appointmentToReturn.setDepartment(department2);
		appointmentToReturn.setDateOfVisit(dateToReturn);
		appointmentToReturn.setPurposeOfVisit("Testing");
		
		Mockito.when(repo.findByDate(date)).thenReturn(List.of(appointmentToReturn));
		
		String check = service.checkIfAppointmentIsOK(appointment);
		assertThat(check).isEqualTo("OK");	
	}
	
	@Test
	public void testCheckIfAppointmentIsOK_DepartmentFull() {
		Date date = appointment.getDateOfVisit();
		Department department = appointment.getDepartment();
		department.setMaxCustomer(2);
		
		Customer customer2 = new Customer();
		
		Appointment appointmentToReturn = new Appointment();
		appointmentToReturn.setCustomer(customer2);
		appointmentToReturn.setDepartment(department);
		appointmentToReturn.setDateOfVisit(date);
		appointmentToReturn.setPurposeOfVisit("Testing");
		
		Mockito.when(repo.findByDate(date)).thenReturn(List.of(appointmentToReturn, appointmentToReturn));
		
		String check = service.checkIfAppointmentIsOK(appointment);
		assertThat(check).isEqualTo("DeptError");
	}
	
	@Test
	public void testCheckIfAppointmentIsOK_DepartmentOK() {
		Date date = appointment.getDateOfVisit();
		Department department = appointment.getDepartment();
		department.setMaxCustomer(3);
		
		Customer customer2 = new Customer();
		
		Appointment appointmentToReturn = new Appointment();
		appointmentToReturn.setCustomer(customer2);
		appointmentToReturn.setDepartment(department);
		appointmentToReturn.setDateOfVisit(date);
		appointmentToReturn.setPurposeOfVisit("Testing");
		
		Mockito.when(repo.findByDate(date)).thenReturn(List.of(appointmentToReturn, appointmentToReturn));
		
		String check = service.checkIfAppointmentIsOK(appointment);
		assertThat(check).isEqualTo("OK");
	}

}
