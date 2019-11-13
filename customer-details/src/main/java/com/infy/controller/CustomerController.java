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
import com.infy.util.Converter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/customers")
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${AddressUri}")
	String addressUri;
	
	@PostMapping
	ResponseEntity<Customer> addCustomer(@Valid @RequestBody Customer customer) {
		log.info("Begin: addCustomer()");
		
		customer.setCustomerId(UUID.randomUUID().toString());
		
		Address address =  Converter.getAddressFromCustomer(customer);
		
		restTemplate.postForObject(addressUri, address, Address.class);
		Customer res = customerService.addCustomer(customer);
		
		log.info("End: addCustomer()");
		return new ResponseEntity<Customer>(res,HttpStatus.CREATED);
	}
	
	@PutMapping
	void updateCustomer(@Valid @RequestBody Customer customer) throws CustomerException {
		log.info("Begin: updateCustomer()");
		
		Address address = Converter.getAddressFromCustomer(customer);
		restTemplate.put(addressUri, address);
		customerService.updateCustomer(customer);

		log.info("End: updateCustomer()");
	}
	
	@DeleteMapping("/{uniqueId}")
	void deleteCustomer(@NotBlank @PathVariable String uniqueId, @RequestParam String type) throws CustomerException {
		log.info("Begin: deleteCustomer()");
		String customerId = null;

		if("emailAddress".equals(type))
			customerId = customerService.deleteCustomerByEmailAddress(uniqueId);
		else if("customerId".equals(type))
			customerId = customerService.deleteCustomerByCustomerId(uniqueId);
		restTemplate.delete(addressUri+"/"+customerId);

		log.info("End: deleteCustomer()");
	}

	@GetMapping("/{uniqueId}")
	ResponseEntity<Customer> findCustomerByUniqueId(@NotBlank @PathVariable String uniqueId, @RequestParam String type)
										throws CustomerException{
		log.info("Begin: findCustomerByUniqueId()");
		Customer customer = null;
		if("emailAddress".equals(type))
			customer = customerService.findCustomerByEmailAddress(uniqueId);
		else if("customerId".equals(type))
			customer = customerService.findCustomerByCustomerId(uniqueId);
		Address address = restTemplate.getForObject(addressUri+"/"+customer.getCustomerId(),
											Address.class);
		address.setCustomerId(null);
		customer.setAddress(address);
		
		log.info("End: findCustomerByUniqueId()");
		return new ResponseEntity<>(customer,HttpStatus.OK);
	}
	
	@GetMapping("/filters")
	ResponseEntity<List<Customer>> findCustomerByFirstNameOrLastName(@RequestParam(required = false) String firstName
											,@RequestParam(required = false) String lastName) throws CustomerException {
		log.info("Begin: findCustomerByFirstNameOrLastName()");
		List<Customer> customers = null;
		customers = customerService.findCustomerByFirstNameOrLastName(firstName, lastName);
		customers.stream().forEach(customer->{
			Address address = restTemplate.getForObject(addressUri+"/"+customer.getCustomerId(), Address.class);
			address.setCustomerId(null);
			customer.setAddress(address);
		});
		log.info("End: findCustomerByFirstNameOrLastName()");
		return new ResponseEntity<>(customers,HttpStatus.OK);
	}
}
