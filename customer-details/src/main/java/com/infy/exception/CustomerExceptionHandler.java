package com.infy.exception;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.infy.error.CustomerError;
import com.mongodb.MongoWriteException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class CustomerExceptionHandler extends ResponseEntityExceptionHandler{
	@ExceptionHandler(value = CustomerException.class)
	void customerExceptionHandler(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.NOT_FOUND.value());
	}
	
	@ExceptionHandler(value = MongoWriteException.class)
	void mongoExceptionHandler(HttpServletResponse response, MongoWriteException exception) throws IOException {
		log.info(exception.getMessage());
		if(exception.getMessage().contains("emailAddress dup key"))
			response.sendError(HttpStatus.CONFLICT.value(), CustomerError.DUPLICATE_EMAIL_ID);
		else
			throw exception;
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		
		body.put("timestamp", new Date());
		body.put("status", status.value());
		body.put("error", "Bad Request");
		body.put("message",CustomerError.VALIDATION_FAILED);
		
		List<String> errors = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(error->error.getDefaultMessage())
				.collect(Collectors.toList());
		body.put("errors", errors);
		return new ResponseEntity<Object>(body, headers, status);
	}
	
}
