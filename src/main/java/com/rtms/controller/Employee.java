package com.rtms.controller;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	//Employee
	
	@PostMapping("addEmployee")
	public ResponseEntity<Response> addEmployee(@RequestBody  Map<String, Object> json){
		JSONObject data = employeeService.addEmployee(json.get("firstName").toString(),json.get("lastName").toString(),json.get("address").toString(),json.get("city").toString(),json.get("designationID").toString(),json.get("phoneNo").toString());
		return new ResponseEntity<Response>(new Response(data.toMap()),HttpStatus.OK);
	}
	
	@GetMapping("getEmployees")
	public ResponseEntity<Response> getEmployees(){
		JSONObject data = employeeService.getEmployees();
		return new ResponseEntity<Response>(new Response(data.toMap()),HttpStatus.OK);
	}
	
	@GetMapping("getEmployeeByID")
	public ResponseEntity<Response> getEmployeeByID(@RequestBody  Map<String, Object> json){
		JSONObject data = employeeService.getEmployeeByID(json.get("id").toString());
		return new ResponseEntity<Response>(new Response(data.toMap()),HttpStatus.OK);
	}
	
	@PatchMapping("updateEmployee")
	public ResponseEntity<Response> updateEmployee(@RequestBody  Map<String, Object> json){
		System.out.println();
		JSONObject data = employeeService.updateEmployee(json.get("employeeID").toString(),json.get("firstName").toString(),json.get("lastName").toString(),json.get("address").toString(),json.get("city").toString(),json.get("phoneNo").toString());
		return new ResponseEntity<Response>(new Response(data.toMap()),HttpStatus.OK);
	}
	
	@DeleteMapping("deleteEmployee")
	public ResponseEntity<Response> deleteEmployee(@RequestBody  Map<String, Object> json){
		JSONObject data = employeeService.deleteEmployee(json.get("employeeID").toString());
		return new ResponseEntity<Response>(new Response(data.toMap()),HttpStatus.OK);
	}
	
	
	
	//Designation
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
	
	@GetMapping("getDesignation")
	public ResponseEntity<Response> getDesignation(){
		JSONObject data = employeeService.getDesignation();
		return new ResponseEntity<Response>(new Response(data.toMap()),HttpStatus.OK);
	}
	
}
