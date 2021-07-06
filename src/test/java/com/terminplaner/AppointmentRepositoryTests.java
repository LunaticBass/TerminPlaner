package com.terminplaner;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.terminplaner.entity.Appointment;
import com.terminplaner.entity.Customer;
import com.terminplaner.entity.Department;
import com.terminplaner.repository.AppointmentRepository;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class AppointmentRepositoryTests {

	@Autowired
	private AppointmentRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateAppointment() {
		Customer customer = entityManager.find(Customer.class, 1);
		Department department = entityManager.find(Department.class, 3);
		
		Appointment appointment = new Appointment(department, customer);
		 //appointment.setDateOfVisit(LocalDateTime.of(2021, 6, 12, 10, 30));
		appointment.setPurposeOfVisit("TESTING");
		
		Appointment savedAppointment = repo.save(appointment);
		
		assertThat(savedAppointment.getId()).isGreaterThan(0);
		assertThat(savedAppointment.getCustomer()).isEqualTo(customer);
		assertThat(savedAppointment.getDepartment()).isEqualTo(department);
		assertThat(savedAppointment.getDateOfVisit()).isEqualTo(appointment.getDateOfVisit());
		assertThat(savedAppointment.getPurposeOfVisit()).isEqualTo(appointment.getPurposeOfVisit());
	}
	
	@Test
	public void testFindByDate() {

		long millisec = LocalDate.of(2021, 7, 2).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();

		Date date = new Date(millisec);
		
		List<Appointment> list = repo.findByDate(date);
		
		assertThat(list.size()).isEqualTo(2);
	}
}
