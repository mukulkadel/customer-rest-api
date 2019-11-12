package com.infy.util;

import com.infy.document.AddressDocument;
import com.infy.dto.Address;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Converter {
	
	public static Address toAddress(AddressDocument document) {
		log.info("Start: toAddress()");
		if(document==null)
			return null;
		
		Address address = new Address();
		address.setCity(document.getCity());
		address.setCountry(document.getCountry());
		address.setCustomerId(document.getCustomerId());
		address.setState(document.getState());
		address.setZipCode(document.getZipCode());

		log.info("End: toAddress()");
		return address;
	}
	public static AddressDocument fromAddress(Address address) {
		log.info("Start: fromAddress()");
		if(address==null)
			return null;
		
		AddressDocument document = new AddressDocument();
		document.setCity(address.getCity());
		document.setCountry(address.getCountry());
		document.setCustomerId(address.getCustomerId());
		document.setState(address.getState());
		document.setZipCode(address.getZipCode());

		log.info("End: fromAddress()");
		return document;
	}
}
