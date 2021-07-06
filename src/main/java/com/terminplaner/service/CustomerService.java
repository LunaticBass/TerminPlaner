package com.terminplaner.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.terminplaner.entity.Customer;
import com.terminplaner.repository.CustomerRepository;

@Service
@Transactional
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepo;
	
	
	public Customer save(Customer customer) {
		return customerRepo.save(customer);
	}

	public boolean isEmailUnique(Integer id, String email) {
		Customer custByEmail = customerRepo.getUserByEmail(email);
		
		if (custByEmail == null) return true;
		
		boolean isCreatingNew = (id == null);
		
		if (isCreatingNew) {
			if(custByEmail != null) return false;
		} else {
			if (custByEmail.getId() != id) {
				return false;
			}
		}
		
		return true;
	}
	
	public Customer findById(Integer id) {
		return customerRepo.findById(id).get();
	}
	
	public List<Customer> findByKeyword(String keyword) {
		return customerRepo.findByKeyword(keyword);
	}

}
