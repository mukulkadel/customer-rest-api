package com.infy.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.infy.dto.Address;
import com.infy.dto.Customer;
import com.infy.exception.CustomerException;
import com.infy.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${AddressUri}")
	String addressUri;

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PostMapping
	ResponseEntity<Customer> addCustomer(@Valid @RequestBody Customer customer) {
		logger.info("Begin: addCustomer()");
		
		customer.setCustomerId(UUID.randomUUID().toString());
		
		Address address =  new Address();
		address.setCustomerId(customer.getCustomerId());
		if(customer.getAddress() != null) {
			address.setCity(customer.getAddress().getCity());
			address.setCountry(customer.getAddress().getCountry());
			address.setState(customer.getAddress().getState());
			address.setZipCode(customer.getAddress().getZipCode());
		}
		restTemplate.postForObject(addressUri, address, Address.class);
		Customer res = customerService.addCustomer(customer);
		
		logger.info("End: addCustomer()");
		return new ResponseEntity<Customer>(res,HttpStatus.CREATED);
	}
	
	@PutMapping
	ResponseEntity<Customer> updateCustomer(@Valid @RequestBody Customer customer) {
		logger.info("Begin: updateCustomer()");
		try {
			Address address = new Address();
			address.setCustomerId(customer.getCustomerId());
			
			if(customer.getAddress() != null) {
				address.setCity(customer.getAddress().getCity());
				address.setCountry(customer.getAddress().getCountry());
				address.setState(customer.getAddress().getState());
				address.setZipCode(customer.getAddress().getZipCode());
			}
			restTemplate.put(addressUri, address);
			customerService.updateCustomer(customer);
		} catch (CustomerException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					e.getMessage());
		}
		logger.info("End: updateCustomer()");
		return new ResponseEntity<Customer>(customer,HttpStatus.OK);
	}
	
	@DeleteMapping("/{uniqueId}")
	ResponseEntity<String> deleteCustomer(@NotBlank @PathVariable String uniqueId, @RequestParam String type) {
		logger.info("Begin: deleteCustomer()");
		String customerId = null;
		try {
			if("emailAddress".equals(type))
				customerId = customerService.deleteCustomerByEmailAddress(uniqueId);
			else if("customerId".equals(type))
				customerId = customerService.deleteCustomerByCustomerId(uniqueId);
			restTemplate.delete(addressUri+"/"+customerId);
		} catch (CustomerException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					e.getMessage());
		}
		logger.info("End: deleteCustomer()");
		return new ResponseEntity<String>(customerId,HttpStatus.OK);
	}

	@GetMapping("/{uniqueId}")
	ResponseEntity<Customer> findCustomerByUniqueId(@NotBlank @PathVariable String uniqueId, @RequestParam String type) {
		logger.info("Begin: findCustomerByUniqueId()");
		Customer customer = null;
		try {
			if("emailAddress".equals(type))
				customer = customerService.findCustomerByEmailAddress(uniqueId);
			else if("customerId".equals(type))
				customer = customerService.findCustomerByCustomerId(uniqueId);
			Address address = restTemplate.getForObject(addressUri+"/"+customer.getCustomerId(),
												Address.class);
			address.setCustomerId(null);
			customer.setAddress(address);
		} catch (CustomerException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					e.getMessage());
		}
		logger.info("End: findCustomerByUniqueId()");
		return new ResponseEntity<>(customer,HttpStatus.OK);
	}
	
	@GetMapping("/filters")
	ResponseEntity<List<Customer>> findCustomerByFirstNameOrLastName(@RequestParam(required = false) String firstName,@RequestParam(required = false) String lastName) {
		logger.info("Begin: findCustomerByFirstNameOrLastName()");
		List<Customer> customers = null;
		try {
			customers = customerService.findCustomerByFirstNameOrLastName(firstName, lastName);
			customers.stream().forEach(customer->{
				Address address = restTemplate.getForObject(addressUri+"/"+customer.getCustomerId(), Address.class);
				address.setCustomerId(null);
				customer.setAddress(address);
			});
		} catch (CustomerException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					e.getMessage());
		}
		logger.info("End: findCustomerByFirstNameOrLastName()");
		return new ResponseEntity<>(customers,HttpStatus.OK);
	}
}
