package com.infy.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.infy.document.CustomerDocument;
import com.infy.dto.Customer;

public class Converter {

	private static Logger logger = LoggerFactory.getLogger(Converter.class);

	public static Customer toCustomer(CustomerDocument document) {
		logger.info("Begin: toCustomer()");
		if(document==null)
			return null;
		
		Customer customer = new Customer();
		customer.setCustomerId(document.getCustomerId());
		customer.setEmailAddress(document.getEmailAddress());
		customer.setFirstName(document.getFirstName());
		customer.setLastName(document.getLastName());
		customer.setPassword(document.getPassword());
		customer.setPhoneNumber(document.getPhoneNumber());
		
		logger.info("End: toCustomer()");
		return customer;
	}
	
	public static CustomerDocument toCustomerEntity(Customer customer) {
		logger.info("Begin: toCustomer()");
		if(customer==null)
			return null;
		
		CustomerDocument document = new CustomerDocument();
		document.setCustomerId(customer.getCustomerId());
		document.setEmailAddress(customer.getEmailAddress());
		document.setFirstName(customer.getFirstName());
		document.setLastName(customer.getLastName());
		document.setPassword(customer.getPassword());
		document.setPhoneNumber(customer.getPhoneNumber());
		
		logger.info("End: toCustomerEntity()");
		return document;
	}
}
