package com.infy.service;

import java.util.List;

import com.infy.dto.Customer;
import com.infy.exception.CustomerException;

public interface CustomerService {
	Customer addCustomer(Customer customer);
	void updateCustomer(Customer customer) throws CustomerException;
	String deleteCustomerByEmailAddress(String emailAddress) throws CustomerException;
	String deleteCustomerByCustomerId(String customerId) throws CustomerException;
	Customer findCustomerByEmailAddress(String emailAddress) throws CustomerException;
	Customer findCustomerByCustomerId(String customerId) throws CustomerException;
	List<Customer> findCustomerByFirstNameOrLastName(String firstName, String lastName) throws CustomerException;
	List<Customer> findAllCustomer();
}
