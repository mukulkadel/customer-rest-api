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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	CustomerService customerService;
	
	@GetMapping
	ResponseEntity<List<Customer>> findAllCustomer(){
		log.info("Begin: findAllCustomer()");
		List<Customer> customers =  customerService.findAllCustomer();
		log.info("End: findAllCustomer()");
		return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
	}
}
