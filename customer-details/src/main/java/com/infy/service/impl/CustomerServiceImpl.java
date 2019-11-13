package com.infy.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infy.document.CustomerDocument;
import com.infy.dto.Customer;
import com.infy.error.CustomerError;
import com.infy.exception.CustomerException;
import com.infy.repository.CustomerRepository;
import com.infy.service.CustomerService;
import com.infy.util.Converter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	CustomerRepository customerRepository;

	@Override
	public Customer addCustomer(Customer customer) {
		log.info("Begin: addCustomer()");
		
		CustomerDocument document = Converter.toCustomerEntity(customer);
		customerRepository.save(document);
		customer = Converter.toCustomer(document);
		customer.setPassword(null);
		log.info("End: addCustomer()");
		return customer;
	}

	@Override
	public void updateCustomer(Customer customer) throws CustomerException{
		log.info("Begin: updateCustomer()");
		CustomerDocument document = customerRepository.findByCustomerId(customer.getCustomerId());
		if(document==null) 
			throw new CustomerException(CustomerError.INVALID_CUSTOMER_ID);
		
		document.setEmailAddress(customer.getEmailAddress());
		document.setFirstName(customer.getFirstName());
		document.setLastName(customer.getLastName());
		document.setPassword(customer.getPassword());
		document.setPhoneNumber(customer.getPhoneNumber());
		
		customerRepository.save(document);
		log.info("Customer updated with id: " + customer.getCustomerId());
		log.info("End: updateCustomer()");
	}

	@Override
	public String deleteCustomerByEmailAddress(String emailAddress) throws CustomerException {
		log.info("Begin: deleteCustomerByEmailAddress()");
		String customerId;
		CustomerDocument document = customerRepository.findByEmailAddress(emailAddress);
		if(document==null)
			throw new CustomerException(CustomerError.INVALID_EMAIL_ID);
		
		customerId = document.getCustomerId();
		customerRepository.delete(document);
		log.info("End: deleteCustomerByEmailAddress()");
		return customerId;
	}

	@Override
	public String deleteCustomerByCustomerId(String customerId) throws CustomerException {
		log.info("Begin: deleteCustomerByCustomerId()");
		CustomerDocument document = customerRepository.findByCustomerId(customerId);
		if(document==null)
			throw new CustomerException(CustomerError.INVALID_CUSTOMER_ID);
		
		customerRepository.delete(document);
		log.info("End: deleteCustomerByCustomerId()");
		return customerId;
	}

	@Override
	public Customer findCustomerByEmailAddress(String emailAddress) throws CustomerException {
		log.info("Begin: findCustomerByEmailAddress()");
		CustomerDocument document = customerRepository.findByEmailAddress(emailAddress);
		if(document==null)
			throw new CustomerException(CustomerError.INVALID_EMAIL_ID);
		
		Customer customer = Converter.toCustomer(document);
		customer.setPassword(null);
		log.info("End: findCustomerByEmailAddress()");
		return customer;
	}

	@Override
	public Customer findCustomerByCustomerId(String customerId) throws CustomerException {
		log.info("Begin: findCustomerByCustomerId()");
		CustomerDocument document = customerRepository.findByCustomerId(customerId);
		if(document==null)
			throw new CustomerException(CustomerError.INVALID_CUSTOMER_ID);
		
		Customer customer = Converter.toCustomer(document);
		customer.setPassword(null);
		log.info("End: findCustomerByCustomerId()");
		return customer;
	}

	@Override
	public List<Customer> findCustomerByFirstNameOrLastName(String firstName, String lastName) throws CustomerException {
		log.info("Begin: findCustomerByFirstNameOrLastName()");
		List<CustomerDocument> documents = customerRepository.findByFirstNameOrLastName(firstName, lastName);
		if(documents.isEmpty())
			throw new CustomerException(CustomerError.INVALID_FIRSTNAME_OR_LASTNAME);
		
		List<Customer> customers = documents.stream()
										.map(document->{
											Customer customer = Converter.toCustomer(document);
											customer.setPassword(null);
											return customer;
										}).collect(Collectors.toList());
		log.info("End: findCustomerByFirstNameOrLastName()");
		return customers;
	}

	@Override
	public List<Customer> findAllCustomer() {
		log.info("Begin: findAllCustomer()");
		List<CustomerDocument> documents = customerRepository.findAll();
		List<Customer> customers = documents.stream()
										.map(Converter::toCustomer)
										.collect(Collectors.toList());
		log.info("End: findAllCustomer()");
		return customers;
	}

}
