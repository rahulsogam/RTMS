package com.rtms.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FoodInventoryService {
	@Autowired
	DataSource dataSource;
	
	PreparedStatement preparedStatement=null;
	ResultSet rs;
	JSONArray data ;
	JSONObject cat;
	CallableStatement cs;
	
	Connection connection;
	
	public JSONObject getAllCat() {
		data = new JSONArray() ;
		cat = new JSONObject();
		try {
			connection= dataSource.getConnection();
			cs = connection.prepareCall("{call getAllCategories()}");
			rs=cs.executeQuery();
			while(rs.next()) {
				cat.put("msg", "Success");
				JSONObject result= new JSONObject();
				result.put("CatId",rs.getString("CategoryId"));
				result.put("CatName", rs.getString("CategoryName"));
				data.put(result);
				}
			cat.put("FoodCategories", data);
			}
			catch(Exception e) {
				cat.put("Exception", e.toString());
				e.printStackTrace();
			}
		System.out.println(cat);
		return cat;
	}
	
	public JSONObject addFoodInventory(String inventoryName, int quantity ) {
		//System.out.println(category);
		data = new JSONArray() ;
		cat = new JSONObject();
		try {
			connection= dataSource.getConnection();
			cs = connection.prepareCall("{call addInventory(?,?)}");
			cs.setString(1, inventoryName);
			cs.setInt(2, quantity);
			int a=cs.executeUpdate();
			System.out.println(a);
			if(a!=0) {
				cat.put("msg", "Success");
				}else {
					cat.put("msg", "error");
				}
			}
			catch(Exception e) {
				cat.put("Exception", e.toString());
				e.printStackTrace();
			}
		System.out.println(cat);
		return cat;
	}
	
}
