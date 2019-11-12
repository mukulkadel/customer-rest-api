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
import com.infy.exception.AddressException;
import com.infy.service.AddressService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/address")
public class AddressController {
	
	@Autowired
	private AddressService addressService;
	
	@PostMapping
	ResponseEntity<Address> addAddress(@RequestBody Address address){
		log.info("Begin: addAddress()");
		
		Address res = addressService.addAddress(address);
		
		log.info("End: addAddress()");
		return new ResponseEntity<Address>(res,HttpStatus.CREATED);
	}
	
	@PutMapping
	ResponseEntity<Address> updateAddress(@RequestBody Address address){
		log.info("Begin: updateAddress()");
		
		Address res = null;
		try {
			res = addressService.updateAddress(address);
		}catch(AddressException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
		
		log.info("End: updateAddress()");
		return new ResponseEntity<Address>(res, HttpStatus.OK);
	}
	
	@DeleteMapping("/{customerId}")
	ResponseEntity<String> deleteAddress(@PathVariable String customerId){
		log.info("Begin: deleteAddress()");
		
		String res = null;
		try {
			res = addressService.deleteAddress(customerId);
		}catch(AddressException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
		
		log.info("End: deleteAddress()");
		return new ResponseEntity<String>(res, HttpStatus.OK);
	}
	
	@GetMapping("/{customerId}")
	ResponseEntity<Address> findAddressByCustomerId(@PathVariable String customerId){
		log.info("Begin: findAddressByCustomerId()");
		
		Address res = null;
		try {
			res = addressService.findAddressByCustomerId(customerId);
		}catch(AddressException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
		
		log.info("End: findAddressByCustomerId()");
		return new ResponseEntity<Address>(res, HttpStatus.OK);
	}
}
