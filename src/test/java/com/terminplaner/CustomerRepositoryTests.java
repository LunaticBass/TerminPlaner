package com.terminplaner;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.terminplaner.entity.Customer;
import com.terminplaner.repository.CustomerRepository;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CustomerRepositoryTests {

	@Autowired
	private CustomerRepository repo;
	
	@Test
	public void testCreateCustomer() {
		Customer customer = new Customer("John", "Doe");
		customer.setEmail("johndoe@mail.com");
		customer.setAddress("First Str. 34, 1234 TestCity");
		customer.setPhone(3485532);
		
		Customer savedCustomer = repo.save(customer);
		
		assertThat(savedCustomer.getId()).isGreaterThan(0);
		assertThat(savedCustomer.getFirstName()).isEqualTo(customer.getFirstName());
		assertThat(savedCustomer.getLastName()).isEqualTo(customer.getLastName());
		assertThat(savedCustomer.getEmail()).isEqualTo(customer.getEmail());
		assertThat(savedCustomer.getPhone()).isEqualTo(customer.getPhone());		
	}
}
