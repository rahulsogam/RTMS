package com.rtms.controller;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rtms.Response;
import com.rtms.service.CustomerService;
import com.rtms.service.OrderService;

@RestController
@RequestMapping("/customers")
public class Customers {
	
	@Autowired
	CustomerService customerService;
	
	@GetMapping("getCustomers")
	public ResponseEntity<Response> getcustomers(){
		JSONObject data = customerService.getAllCustomers();
		return new ResponseEntity<Response>(new Response(data.toMap()),HttpStatus.OK);
	}
	
	@PostMapping("addCustomer")
	public ResponseEntity<Response> addOrder(@RequestBody  Map<String, Object> json){
		JSONObject data = customerService.addCustomer(json.get("cust_id").toString());
		return new ResponseEntity<Response>(new Response(data.toMap()),HttpStatus.OK);
	}
}

