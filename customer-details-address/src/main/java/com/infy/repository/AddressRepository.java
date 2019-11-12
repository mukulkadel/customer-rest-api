package com.infy.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.infy.document.AddressDocument;

public interface AddressRepository extends MongoRepository<AddressDocument, String>{
	AddressDocument findByCustomerId(String customerId);
}
