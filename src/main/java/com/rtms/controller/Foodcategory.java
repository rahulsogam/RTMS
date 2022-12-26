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
import com.rtms.service.FoodCategoryService;

@RestController
@RequestMapping("/category")
public class Foodcategory {
	
	@Autowired
	FoodCategoryService foodCategoryService;
	
	@GetMapping("getFoodCat")
	public ResponseEntity<Response> getFood(){
		JSONObject data = foodCategoryService.getAllCat();
		return new ResponseEntity<Response>(new Response(data.toMap()),HttpStatus.OK);
	}
	
	@PostMapping("addCategory")
	public ResponseEntity<Response> addCategory(@RequestBody  Map<String, Object> json){
		JSONObject data = foodCategoryService.addFoodCategory(json.get("categoryName").toString());
		return new ResponseEntity<Response>(new Response(data.toMap()),HttpStatus.OK);
	}
}
