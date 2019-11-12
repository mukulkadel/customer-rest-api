package com.infy.service;

import java.util.List;

import com.infy.dto.Address;
import com.infy.exception.AddressException;

public interface AddressService {
	Address addAddress(Address address);
	Address updateAddress(Address address) throws AddressException;
	String deleteAddress(String customerId) throws AddressException;
	Address findAddressByCustomerId(String customerId) throws AddressException;
	List<Address> findAllAddress();
}
