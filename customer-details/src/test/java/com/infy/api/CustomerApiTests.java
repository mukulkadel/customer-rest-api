package com.infy.api;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infy.controller.CustomerController;
import com.infy.dto.Customer;
import com.infy.exception.CustomerException;
import com.infy.service.CustomerService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = CustomerController.class)
public class CustomerApiTests {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private CustomerService service;
	
	private ObjectMapper om = new ObjectMapper();

	@Test
	public void addCustomerValid() throws Exception {
		Customer customer = new Customer();
		customer.setEmailAddress("john@infy.com");
		customer.setFirstName("John");
		customer.setLastName("Wick");
		customer.setPassword("John@123");
		customer.setPhoneNumber("9876543210");
		
		Customer resCustomer = new Customer();
		resCustomer.setCustomerId("5dc3f826af7f0c70ef5fc71f");
		resCustomer.setEmailAddress("john@infy.com");
		resCustomer.setFirstName("John");
		resCustomer.setLastName("Wick");
		resCustomer.setPassword(null);
		resCustomer.setPhoneNumber("9876543210");
		
		when(service.addCustomer(Mockito.any(Customer.class))).thenReturn(resCustomer);
		
		mvc.perform(post("/customer")
				.content(om.writeValueAsString(customer))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("customerId").value(resCustomer.getCustomerId()))
			.andExpect(jsonPath("emailAddress").value(resCustomer.getEmailAddress()))
			.andExpect(jsonPath("firstName").value(resCustomer.getFirstName()))
			.andExpect(jsonPath("lastName").value(resCustomer.getLastName()))
			.andExpect(jsonPath("password").value(resCustomer.getPassword()))
			.andExpect(jsonPath("phoneNumber").value(resCustomer.getPhoneNumber()));
	}
	
	@Test
	public void addCustomerInValidEmailAddress() throws Exception {
		Customer customer = new Customer();
		customer.setEmailAddress("");
		customer.setFirstName("John");
		customer.setLastName("Wick");
		customer.setPassword("John@123");
		customer.setPhoneNumber("9876543210");
		
		mvc.perform(post("/customer")
				.content(om.writeValueAsString(customer))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}

	@Test
	public void addCustomerInValidPassword() throws Exception {
		Customer customer = new Customer();
		customer.setEmailAddress("john@infy.com");
		customer.setFirstName("John");
		customer.setLastName("Wick");
		customer.setPassword("");
		customer.setPhoneNumber("9876543210");
		
		mvc.perform(post("/customer")
				.content(om.writeValueAsString(customer))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}

	@Test
	public void addCustomerInValidFirstName() throws Exception {
		Customer customer = new Customer();
		customer.setEmailAddress("john@infy.com");
		customer.setFirstName("");
		customer.setLastName("Wick");
		customer.setPassword("John@123");
		customer.setPhoneNumber("9876543210");
		
		mvc.perform(post("/customer")
				.content(om.writeValueAsString(customer))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}

	@Test
	public void addCustomerInValidLastName() throws Exception {
		Customer customer = new Customer();
		customer.setEmailAddress("john@infy.com");
		customer.setFirstName("John");
		customer.setLastName("");
		customer.setPassword("John@123");
		customer.setPhoneNumber("9876543210");
		
		mvc.perform(post("/customer")
				.content(om.writeValueAsString(customer))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}
	/*
	@Test
	public void updateCustomerValid() throws Exception{
		Customer customer = new Customer();
		customer.setCustomerId("5dc3f826af7f0c70ef5fc71f");
		customer.setEmailAddress("john@infy.com");
		customer.setFirstName("John");
		customer.setLastName("Wick");
		customer.setPassword("John@123");
		customer.setPhoneNumber("9876543210");
		
		Customer resCustomer = new Customer();
		resCustomer.setCustomerId("5dc3f826af7f0c70ef5fc71f");
		resCustomer.setEmailAddress("john@infy.com");
		resCustomer.setFirstName("John");
		resCustomer.setLastName("Wick");
		resCustomer.setPassword(null);
		resCustomer.setPhoneNumber("9876543210");
		
		when(service.updateCustomer(Mockito.any(Customer.class))).thenReturn(resCustomer);
		
		mvc.perform(put("/customer")
				.content(om.writeValueAsString(customer))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("customerId").value(resCustomer.getCustomerId()))
		.andExpect(jsonPath("emailAddress").value(resCustomer.getEmailAddress()))
		.andExpect(jsonPath("firstName").value(resCustomer.getFirstName()))
		.andExpect(jsonPath("lastName").value(resCustomer.getLastName()))
		.andExpect(jsonPath("password").value(resCustomer.getPassword()))
		.andExpect(jsonPath("phoneNumber").value(resCustomer.getPhoneNumber()));
	}
	
	@Test
	public void updateCustomerInValidCustomerId() throws Exception{
		Customer customer = new Customer();
		customer.setCustomerId("invalid-id");
		customer.setEmailAddress("john@infy.com");
		customer.setFirstName("John");
		customer.setLastName("Wick");
		customer.setPassword("John@123");
		customer.setPhoneNumber("9876543210");
		
		when(service.updateCustomer(Mockito.any(Customer.class)))
			.thenThrow(new CustomerException("CustomerService.INVALID_CUSTOMER_ID"));
		
		mvc.perform(put("/customer")
				.content(om.writeValueAsString(customer))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound())
		.andExpect(status().reason("Customer not found!"));
	}
	
	@Test
	public void updateCustomerInValidEmailAddress() throws Exception {
		Customer customer = new Customer();
		customer.setCustomerId("5dc3f826af7f0c70ef5fc71f");
		customer.setEmailAddress("");
		customer.setFirstName("John");
		customer.setLastName("Wick");
		customer.setPassword("John@123");
		customer.setPhoneNumber("9876543210");
		
		mvc.perform(put("/customer")
				.content(om.writeValueAsString(customer))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}

	@Test
	public void updateCustomerInValidPassword() throws Exception {
		Customer customer = new Customer();
		customer.setCustomerId("5dc3f826af7f0c70ef5fc71f");
		customer.setEmailAddress("john@infy.com");
		customer.setFirstName("John");
		customer.setLastName("Wick");
		customer.setPassword("");
		customer.setPhoneNumber("9876543210");
		
		mvc.perform(put("/customer")
				.content(om.writeValueAsString(customer))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}

	@Test
	public void updateCustomerInValidFirstName() throws Exception {
		Customer customer = new Customer();
		customer.setCustomerId("5dc3f826af7f0c70ef5fc71f");
		customer.setEmailAddress("john@infy.com");
		customer.setFirstName("");
		customer.setLastName("Wick");
		customer.setPassword("John@123");
		customer.setPhoneNumber("9876543210");
		
		mvc.perform(put("/customer")
				.content(om.writeValueAsString(customer))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}

	@Test
	public void updateCustomerInValidLastName() throws Exception {
		Customer customer = new Customer();
		customer.setCustomerId("5dc3f826af7f0c70ef5fc71f");
		customer.setEmailAddress("john@infy.com");
		customer.setFirstName("John");
		customer.setLastName("");
		customer.setPassword("John@123");
		customer.setPhoneNumber("9876543210");
		
		mvc.perform(put("/customer")
				.content(om.writeValueAsString(customer))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}
		
	@Test
	public void deleteCustomerValid() throws Exception{
		String customerId = "5dc3f826af7f0c70ef5fc71f";
		
		when(service.deleteCustomer(Mockito.anyString())).thenReturn(customerId);
		
		mvc.perform(delete(String.format("/customer/%s", customerId)))
				.andExpect(status().isOk())
				.andExpect(content().string(customerId));
	}

	@Test
	public void deleteCustomerInvalidCustomerId() throws Exception{
		String customerId = "invalid-id";
		String exception = "CustomerService.INVALID_CUSTOMER_ID";
		String exceptionMessage = "Customer not found!";
		
		when(service.deleteCustomer(Mockito.anyString()))
			.thenThrow(new CustomerException(exception));
		
		mvc.perform(delete(String.format("/customer/%s", customerId)))
			.andExpect(status().isNotFound())
			.andExpect(status().reason(exceptionMessage));
	}
	
	@Test
	public void findCustomerByIdValid() throws Exception{
		String customerId = "5dc3f826af7f0c70ef5fc71f";
		
		Customer resCustomer = new Customer();
		resCustomer.setCustomerId("5dc3f826af7f0c70ef5fc71f");
		resCustomer.setEmailAddress("john@infy.com");
		resCustomer.setFirstName("John");
		resCustomer.setLastName("Wick");
		resCustomer.setPassword(null);
		resCustomer.setPhoneNumber("9876543210");
		resCustomer.setZipCode("560100");
		
		when(service.findCustomerById(Mockito.anyString())).thenReturn(resCustomer);
		
		mvc.perform(get(String.format("/customer/%s", customerId)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("customerId").value(resCustomer.getCustomerId()))
				.andExpect(jsonPath("emailAddress").value(resCustomer.getEmailAddress()))
				.andExpect(jsonPath("firstName").value(resCustomer.getFirstName()))
				.andExpect(jsonPath("lastName").value(resCustomer.getLastName()))
				.andExpect(jsonPath("password").value(resCustomer.getPassword()))
				.andExpect(jsonPath("phoneNumber").value(resCustomer.getPhoneNumber()))
				.andExpect(jsonPath("zipCode").value(resCustomer.getZipCode()));
	}
	
	@Test
	public void findCustomerByIdInvalidCustomerId() throws Exception{
		String customerId = "invalid-id";
		String exception = "CustomerService.INVALID_CUSTOMER_ID";
		String exceptionMessage = "Customer not found!";
		
		when(service.findCustomerById(Mockito.anyString()))
			.thenThrow(new CustomerException(exception));
		
		mvc.perform(get(String.format("/customer/%s", customerId)))
			.andExpect(status().isNotFound())
			.andExpect(status().reason(exceptionMessage));
	}
	*/
}
