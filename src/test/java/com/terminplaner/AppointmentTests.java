package com.terminplaner;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import com.terminplaner.entity.Appointment;

public class AppointmentTests {

	@Test
	public void testGetEndDate() {
		Appointment appointment = new Appointment();
		appointment.setPurposeOfVisit("Testing");
		
		LocalDateTime dateTime = LocalDateTime.of(2021, 2, 12, 10, 30);
		Date start = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
		
		appointment.setDateOfVisit(start);
		
		Date end = appointment.getEndDate();
		
		long duration  = end.getTime() - start.getTime();
		long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
		
		assertThat(diffInMinutes).isEqualTo(Appointment.DURATION_TESTING);
		
		appointment.setPurposeOfVisit("Click & Collect");
		
		end = appointment.getEndDate();
		duration  = end.getTime() - start.getTime();
		diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
		
		assertThat(diffInMinutes).isEqualTo(Appointment.DURATION_CNC);
	}
}
