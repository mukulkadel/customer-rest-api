package com.infy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.infy.dto.Address;
import com.infy.error.AddressError;
import com.infy.exception.AddressException;
import com.infy.service.AddressService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/addresses")
public class AddressController {
	
	@Autowired
	private AddressService addressService;
	
	@PostMapping
	ResponseEntity<Address> addAddress(@RequestBody Address addressIn){
		log.info("Begin: addAddress()");
		
		Address addressOut = addressService.addAddress(addressIn);
		
		log.info("End: addAddress()");
		return new ResponseEntity<Address>(addressOut,HttpStatus.CREATED);
	}
	
	@PutMapping
	void updateAddress(@RequestBody Address addressIn) throws AddressException{
		log.info("Begin: updateAddress()");
		
		addressService.updateAddress(addressIn);
		
		log.info("End: updateAddress()");
	}
	
	@DeleteMapping("/{customerId}")
	void deleteAddress(@PathVariable String customerIdIn) throws AddressException{
		log.info("Begin: deleteAddress()");
		
		addressService.deleteAddress(customerIdIn);
		
		log.info("End: deleteAddress()");
	}
	
	@GetMapping("/{customerId}")
	ResponseEntity<Address> findAddressByCustomerId(@PathVariable String customerId) throws AddressException{
		log.info("Begin: findAddressByCustomerId()");
		
		Address address = addressService.findAddressByCustomerId(customerId);

		log.info("End: findAddressByCustomerId()");
		return new ResponseEntity<Address>(address, HttpStatus.OK);
	}
}
