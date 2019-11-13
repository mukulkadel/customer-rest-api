package com.infy.error;

import org.springframework.http.HttpStatus;

public class AddressError {
	public static final HttpStatus INVALID_CUSTOMER_ID_CODE = HttpStatus.NOT_FOUND;
	public static final String INVALID_CUSTOMER_ID_MESSAGE = "Customer address not found!";
}
