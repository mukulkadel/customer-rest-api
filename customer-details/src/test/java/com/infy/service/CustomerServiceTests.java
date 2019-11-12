package com.infy.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.infy.document.CustomerDocument;
import com.infy.dto.Customer;
import com.infy.exception.CustomerException;
import com.infy.repository.CustomerRepository;
import com.infy.service.CustomerService;
import com.infy.service.CustomerServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceTests {
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Mock
	CustomerRepository repository;
	
	@InjectMocks
	CustomerService service = new CustomerServiceImpl();
	
	@Test
	public void addCustomerValid() {
		Customer customer = new Customer();
		when(repository.save(Mockito.any())).thenReturn(new CustomerDocument());
		Assert.assertTrue(service.addCustomer(customer)!=null);
	}
	
	@Test
	public void updateCustomerInvalidCustomerId() throws CustomerException {
		expectedException.expect(CustomerException.class);
		expectedException.expectMessage("CustomerService.INVALID_CUSTOMER_ID");
		
		Optional<CustomerDocument> optEntity = Optional.empty();
		Customer customer = new Customer();
		customer.setCustomerId("hgghjgffgh");
		when(repository.findById(Mockito.anyString())).thenReturn(optEntity);
		
		service.updateCustomer(customer);
	}
	
	@Test
	public void updateCustomerValid() throws CustomerException {
		
		Customer customer = new Customer();
		customer.setCustomerId("hgghjgffgh");
		
		CustomerDocument entity = new CustomerDocument();
		Optional<CustomerDocument> optEntity = Optional.of(entity);

		when(repository.findById(Mockito.anyString())).thenReturn(optEntity);
		when(repository.save(Mockito.any())).thenReturn(entity);
		
		service.updateCustomer(customer);
	}
	/*
	@Test
	public void deleteCustomerInvalidCustomerId() throws CustomerException {
		expectedException.expect(CustomerException.class);
		expectedException.expectMessage("CustomerService.INVALID_CUSTOMER_ID");
		
		String customerId = "hgghjgffgh";
		Optional<CustomerDocument> optEntity = Optional.empty();
		when(repository.findById(Mockito.anyString())).thenReturn(optEntity);
		
		service.deleteCustomer(customerId);
	}
	
	@Test
	public void deleteCustomerValid() throws CustomerException {
		
		String customerId = "hgghjgffgh";
		Optional<CustomerDocument> optEntity = Optional.of(new CustomerDocument());
		
		when(repository.findById(Mockito.anyString())).thenReturn(optEntity);
		
		doNothing().when(repository)
				.deleteById(Mockito.anyString());
		
		Assert.assertTrue(customerId.equals(service.deleteCustomer(customerId)));
	}
	
	@Test
	public void findCustomerByIdInvalidCustomerId() throws CustomerException {
		expectedException.expect(CustomerException.class);
		expectedException.expectMessage("CustomerService.INVALID_CUSTOMER_ID");
		
		String customerId = "hgghjgffgh";
		Optional<CustomerDocument> optEntity = Optional.empty();
		when(repository.findById(Mockito.anyString())).thenReturn(optEntity);
		
		service.findCustomerById(customerId);
	}
	
	@Test
	public void findCustomerByIdValid() throws CustomerException {
		
		String customerId = "hgghjgffgh";
		CustomerDocument entity = new CustomerDocument();
		entity.setCustomerId(customerId);
		Optional<CustomerDocument> optEntity = Optional.of(entity);
		
		when(repository.findById(Mockito.anyString())).thenReturn(optEntity);
		
		Assert.assertTrue(customerId.equals(service.findCustomerById(customerId).getCustomerId()));
	}
	*/
}
