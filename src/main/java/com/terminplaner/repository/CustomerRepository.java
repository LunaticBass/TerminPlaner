package com.terminplaner.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.terminplaner.entity.Customer;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Integer> {

	@Query("SELECT c FROM Customer c WHERE c.email = :email")
	public Customer getUserByEmail(@Param("email") String email);
	
	@Query("SELECT c FROM Customer c WHERE CONCAT(c.id, ' ', c.email, ' ',"
			+ " c.firstName, ' ', c.lastName) LIKE %?1%")
	public List<Customer> findByKeyword(String keyword);

}
