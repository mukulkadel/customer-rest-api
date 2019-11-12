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

import com.infy.dto.Customer;
import com.infy.service.CustomerService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	CustomerService customerService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping
	ResponseEntity<List<Customer>> findAllCustomer(){
		logger.info("Begin: findAllCustomer()");
		List<Customer> customers =  customerService.findAllCustomer();
		logger.info("End: findAllCustomer()");
		return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
	}
}