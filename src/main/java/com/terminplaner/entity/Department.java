package com.terminplaner.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "department")
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;	
	
	@Column(name = "name", nullable = false, unique = true)
	private String name;
	private int maxCustomer;
	
	public Department() {
	}

	public Department(Integer id, String name, int maxCustomer) {
		this.id = id;
		this.name = name;
		this.maxCustomer = maxCustomer;
	}

	public Department(String name, int maxCustomer) {
		super();
		this.name = name;
		this.maxCustomer = maxCustomer;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMaxCustomer() {
		return maxCustomer;
	}

	public void setMaxCustomer(int maxCustomer) {
		this.maxCustomer = maxCustomer;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	
}
