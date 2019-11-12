package com.infy.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.infy.document.CustomerDocument;

public interface CustomerRepository extends MongoRepository<CustomerDocument, String> {

	CustomerDocument findByCustomerId(String customerId);
	CustomerDocument findByEmailAddress(String emailAddress);
	List<CustomerDocument> findByFirstNameOrLastName(String firstName, String lastName);
}
