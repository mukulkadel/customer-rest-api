package com.infy.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.infy.document.CustomerDocument;
import com.infy.dto.Address;
import com.infy.dto.Customer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Converter {

	public static Customer toCustomer(CustomerDocument document) {
		log.info("Begin: toCustomer()");
		if(document==null)
			return null;
		
		Customer customer = new Customer();
		customer.setCustomerId(document.getCustomerId());
		customer.setEmailAddress(document.getEmailAddress());
		customer.setFirstName(document.getFirstName());
		customer.setLastName(document.getLastName());
		customer.setPassword(document.getPassword());
		customer.setPhoneNumber(document.getPhoneNumber());
		
		log.info("End: toCustomer()");
		return customer;
	}
	
	public static CustomerDocument toCustomerEntity(Customer customer) {
		log.info("Begin: toCustomer()");
		if(customer==null)
			return null;
		
		CustomerDocument document = new CustomerDocument();
		document.setCustomerId(customer.getCustomerId());
		document.setEmailAddress(customer.getEmailAddress());
		document.setFirstName(customer.getFirstName());
		document.setLastName(customer.getLastName());
		document.setPassword(customer.getPassword());
		document.setPhoneNumber(customer.getPhoneNumber());
		
		log.info("End: toCustomerEntity()");
		return document;
	}
	
	public static Address getAddressFromCustomer(Customer customer) {
		log.info("Begin: getAddressFromCustomer()");
		if(customer==null)
			return null;
		Address address = new Address();
		address.setCustomerId(customer.getCustomerId());

		if(customer.getAddress() != null) {
			address.setCity(customer.getAddress().getCity());
			address.setCountry(customer.getAddress().getCountry());
			address.setState(customer.getAddress().getState());
			address.setZipCode(customer.getAddress().getZipCode());
		}
		log.info("End: getAddressFromCustomer()");
		return address;
	}
}
