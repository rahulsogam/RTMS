package com.rtms.controller;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rtms.Response;
import com.rtms.service.FoodInventoryService;

@RequestMapping("/Inventory")
@RestController
public class FoodInventory {
	
	@Autowired
	FoodInventoryService foodInventoryService;
	
	@PostMapping("addInventory")
	public ResponseEntity<Response> addCategory(@RequestBody  Map<String, Object> json){
		JSONObject data = foodInventoryService.addFoodInventory(json.get("inventoryName").toString(),(Integer)json.get("availabelQuantity"));
		return new ResponseEntity<Response>(new Response(data.toMap()),HttpStatus.OK);
	}
}
