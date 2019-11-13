package com.infy.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infy.document.AddressDocument;
import com.infy.dto.Address;
import com.infy.error.AddressError;
import com.infy.exception.AddressException;
import com.infy.repository.AddressRepository;
import com.infy.service.AddressService;
import com.infy.util.Converter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class AddressServiceImpl implements AddressService {
	
	@Autowired
	private AddressRepository addressRepository;

	@Override
	public Address addAddress(Address address) {
		log.info("Start: addAddress()");
		
		AddressDocument document = Converter.fromAddress(address);
		addressRepository.save(document);
		
		log.info("End: addAddress()");
		return address;
	}

	@Override
	public void updateAddress(Address address) throws AddressException {
		log.info("Start: updateAddress()");
		
		AddressDocument document = addressRepository.findByCustomerId(address.getCustomerId());
		if (document == null)
			throw new AddressException(AddressError.INVALID_CUSTOMER_ID_MESSAGE);
		document.setCity(address.getCity());
		document.setCountry(address.getCountry());
		document.setState(address.getState());
		document.setZipCode(address.getZipCode());
		
		addressRepository.save(document);
		
		log.info("End: updateAddress()");
	}

	@Override
	public void deleteAddress(String customerId) throws AddressException {
		log.info("Start: deleteAddress()");
		
		AddressDocument document = addressRepository.findByCustomerId(customerId);
		if (document == null)
			throw new AddressException(AddressError.INVALID_CUSTOMER_ID_MESSAGE);
		
		addressRepository.delete(document);
		
		log.info("End: deleteAddress()");
	}

	@Override
	public Address findAddressByCustomerId(String customerId) throws AddressException {
		log.info("Start: findAddressByCustomerId()");
		
		AddressDocument document = addressRepository.findByCustomerId(customerId);
		if (document == null)
			throw new AddressException(AddressError.INVALID_CUSTOMER_ID_MESSAGE);
		
		Address address = Converter.toAddress(document);
		
		log.info("End: findAddressByCustomerId()");
		return address;
	}

	@Override
	public List<Address> findAllAddress() {
		log.info("Start: findAllAddress()");
		
		List<AddressDocument> documents = addressRepository.findAll();
		List<Address> addresses = documents.stream()
										.map(Converter::toAddress)
										.collect(Collectors.toList());
		
		log.info("End: findAllAddress()");
		return addresses;
	}

}
