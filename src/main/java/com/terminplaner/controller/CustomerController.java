package com.terminplaner.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.terminplaner.entity.Customer;
import com.terminplaner.service.CustomerService;

@Controller
public class CustomerController {
	
	@Autowired
	private CustomerService custService;
		
	@GetMapping("/customers/{date}")
	public String listCustomers(@Param("keyword") String keyword, @PathVariable(name="date") Long millisec, Model model) {
		
		List<Customer> custList = custService.findByKeyword(keyword);
		model.addAttribute("custList", custList);
		model.addAttribute("millisec", millisec);
		
		return "customers";
	}	

}
