package com.terminplaner.entity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "appointments")
public class Appointment {
	
	@Transient
	public static int DURATION_TESTING = 60;

	@Transient
	public static int DURATION_CNC = 30;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date dateOfVisit;
	
	@ManyToOne
	@JoinColumn(name = "dept_id", referencedColumnName = "id", nullable = false)
	private Department department;
	
	private String purposeOfVisit;
	
	@ManyToOne
	@JoinColumn(name = "cust_id", referencedColumnName = "id", nullable = false)
	private Customer customer;

	public Appointment() {
	}

	public Appointment(Customer customer) {
		this.customer = customer;
	}

	public Appointment(Department department, Customer customer) {
		this.department = department;
		this.customer = customer;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDateOfVisit() {
		return dateOfVisit;
	}

	public void setDateOfVisit(Date dateOfVisit) {
		this.dateOfVisit = dateOfVisit;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getPurposeOfVisit() {
		return purposeOfVisit;
	}

	public void setPurposeOfVisit(String purposeOfVisit) {
		this.purposeOfVisit = purposeOfVisit;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	@Transient
	public Date getEndDate() {
		LocalDateTime start = dateOfVisit.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		if (purposeOfVisit.equals("Testing")) {
			return Date.from(start.plusMinutes(DURATION_TESTING).atZone(ZoneId.systemDefault()).toInstant());
		} else {
			return Date.from(start.plusMinutes(DURATION_CNC).atZone(ZoneId.systemDefault()).toInstant());
		}
	}

	@Override
	public String toString() {
		return "Appointment [dateOfVisit=" + dateOfVisit + ", customer=" + customer + ", department=" + department
				+ ", purposeOfVisit=" + purposeOfVisit + "]";
	}	
	
}
