package com.rtms.controller;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rtms.Response;
import com.rtms.service.EmployeeService;


@RestController
@RequestMapping("/employee")
public class Employee {
	
	@Autowired
	EmployeeService employeeService;
	
	@PostMapping("addDesignation")
	public ResponseEntity<Response> addDesignation(@RequestBody  Map<String, Object> json){
		JSONObject data = employeeService.addDesignation(json.get("designationName").toString(),json.get("designationSalary").toString());
		return new ResponseEntity<Response>(new Response(data.toMap()),HttpStatus.OK);
	}
	
	@PatchMapping("changeSalary")
	public ResponseEntity<Response> changeSalary(@RequestBody  Map<String, Object> json){
		JSONObject data = employeeService.changeSalary(json.get("designationID").toString(),json.get("designationSalary").toString());
		return new ResponseEntity<Response>(new Response(data.toMap()),HttpStatus.OK);
	}
	
	@DeleteMapping("deleteDesignation")
	public ResponseEntity<Response> deleteDesignation(@RequestBody  Map<String, Object> json){
		JSONObject data = employeeService.deleteDesignation(json.get("designationID").toString());
		return new ResponseEntity<Response>(new Response(data.toMap()),HttpStatus.OK);
	}
}
