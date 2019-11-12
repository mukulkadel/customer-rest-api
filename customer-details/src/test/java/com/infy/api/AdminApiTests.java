package com.infy.api;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.infy.controller.AdminController;
import com.infy.dto.Customer;
import com.infy.service.CustomerService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = AdminController.class)
public class AdminApiTests {
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private CustomerService service;
	
	@Test
	public void findAllCustomerValid() throws Exception {
		
		List<Customer> customers = new ArrayList<>();
		
		when(service.findAllCustomer()).thenReturn(customers);
		
		mvc.perform(get("/admin"))
			.andExpect(status().isOk())
			.andExpect(content().json("[]"));
	}
	
	@Test
	public void findAllCustomerInValidNull() throws Exception {
		
		List<Customer> customers = null;
		
		when(service.findAllCustomer()).thenReturn(customers);
		
		mvc.perform(get("/admin"))
			.andExpect(status().isOk())
			.andExpect(content().string(""));
	}
}