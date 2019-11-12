package com.infy.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.dto.Address;
import com.infy.service.AddressService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/address-admin")
public class AdminController {
	@Autowired
	AddressService addressService;
	
	@GetMapping
	ResponseEntity<List<Address>> findAllAddress(){
		log.info("Begin: findAllAddress()");
		List<Address> addresses =  addressService.findAllAddress();
		log.info("End: findAllAddress()");
		return new ResponseEntity<List<Address>>(addresses, HttpStatus.OK);
	}
}
