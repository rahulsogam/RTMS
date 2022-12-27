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

import jakarta.transaction.Transactional;


@Service
@Transactional
public class OrderService {
	@Autowired
	DataSource dataSource;
	
	PreparedStatement preparedStatement=null;
	ResultSet rs;
	JSONArray data ;
	JSONObject ord;
	CallableStatement cs;
	
	Connection connection;
	
	public JSONObject getAllOrder() {
		data = new JSONArray() ;
		ord = new JSONObject();
		try {
			connection= dataSource.getConnection();
			cs = connection.prepareCall("{call getOrder()}");
			rs=cs.executeQuery();
			while(rs.next()) {
				ord.put("msg", "Success");
				JSONObject result= new JSONObject();
				result.put("OId",rs.getString("order_id"));
				result.put("CId",rs.getString("cust_id"));
				result.put("date",rs.getString("date"));
				result.put("Item",rs.getString("item"));
				result.put("CatId",rs.getString("CategoryId"));
				result.put("TotalCost",rs.getString("total_cost"));
				result.put("PM",rs.getString("payment_method"));
				data.put(result);
				}
			ord.put("Orders", data);
			}
			catch(Exception e) {
				ord.put("Exception", e.toString());
				e.printStackTrace();
			}
		System.out.println(ord);
		return ord;
	}
	
	public JSONObject addOrder (String order) {
		System.out.println(order);
		data = new JSONArray() ;
		ord = new JSONObject();
		try {
			connection= dataSource.getConnection();
			cs = connection.prepareCall("{call addOrder(?)}");
			cs.setString(1, order );
			int a=cs.executeUpdate();
			System.out.println(a);
			if(a!=0) {
				ord.put("msg", "Success");
				}
			}
			catch(Exception e) {
				ord.put("Exception", e.toString());
				e.printStackTrace();
			}
		System.out.println(ord);
		return ord;
	}
	

}
