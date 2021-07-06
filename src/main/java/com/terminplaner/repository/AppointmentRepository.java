package com.terminplaner.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.terminplaner.entity.Appointment;

public interface AppointmentRepository extends PagingAndSortingRepository<Appointment, Integer> {
	
	@Query("SELECT a FROM Appointment a WHERE Date(a.dateOfVisit) = :date ORDER BY a.dateOfVisit")
	List<Appointment> findByDate(Date date);

}
