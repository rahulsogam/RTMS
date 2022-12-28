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
public class CustomerService {
	@Autowired
	DataSource dataSource;
	
	PreparedStatement preparedStatement=null;
	ResultSet rs;
	JSONArray data ;
	JSONObject cust;
	CallableStatement cs;
	
	Connection connection;
	
	public JSONObject getAllCustomers() {
		data = new JSONArray() ;
		cust = new JSONObject();
		try {
			connection= dataSource.getConnection();
			cs = connection.prepareCall("{call getCustomers()}");
			rs=cs.executeQuery();
			while(rs.next()) {
				cust.put("msg", "Success");
				JSONObject result= new JSONObject();
				result.put("CId",rs.getString("cust_id"));
				result.put("CName",rs.getString("cust_name"));
				result.put("CEmail",rs.getString("cust_email"));
				result.put("CNo",rs.getString("cust_no"));
				result.put("OId",rs.getString("order_id"));
				data.put(result);
				}
			cust.put("Customers", data);
			}
			catch(Exception e) {
				cust.put("Exception", e.toString());
				e.printStackTrace();
			}
		System.out.println(cust);
		return cust;
	}
	
	public JSONObject addCustomer (String customer) {
		System.out.println(customer);
		data = new JSONArray() ;
		cust = new JSONObject();
		try {
			connection= dataSource.getConnection();
			cs = connection.prepareCall("{call addCustomer (?)}");
			cs.setString(1, customer );
			int a=cs.executeUpdate();
			System.out.println(a);
			if(a!=0) {
				cust.put("msg", "Success");
				}
			}
			catch(Exception e) {
				cust.put("Exception", e.toString());
				e.printStackTrace();
			}
		System.out.println(cust);
		return cust;
	}
	

}
