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
import com.rtms.service.OrderService;

@RestController
@RequestMapping("/orders")
public class Orders {
	
	@Autowired
	OrderService orderService;
	
	@GetMapping("getOrder")
	public ResponseEntity<Response> getorder(){
		JSONObject data = orderService.getAllOrder();
		return new ResponseEntity<Response>(new Response(data.toMap()),HttpStatus.OK);
	}
	
	@PostMapping("addOrder")
	public ResponseEntity<Response> addOrder(@RequestBody  Map<String, Object> json){
		JSONObject data = orderService.addOrder(json.get("order_id").toString());
		return new ResponseEntity<Response>(new Response(data.toMap()),HttpStatus.OK);
	}
}

