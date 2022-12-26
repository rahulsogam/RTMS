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
public class EmployeeService {
	@Autowired
	DataSource dataSource;
	
	PreparedStatement preparedStatement=null;
	ResultSet rs;
	JSONArray data ;
	JSONObject emp;
	CallableStatement cs;
	Connection connection;
	
	public JSONObject addDesignation(String designationName, String designationSalary) {
		System.out.println(designationSalary);
		data = new JSONArray() ;
		emp = new JSONObject();
		try {
			connection= dataSource.getConnection();
			cs = connection.prepareCall("{call addDesignation(?,?)}");
			cs.setString(1, designationName);
			cs.setString(2, designationSalary);
			int a=cs.executeUpdate();
			System.out.println(a);
			if(a!=0) {
				emp.put("msg", "Success");
				}
			}
			catch(Exception e) {
				emp.put("Exception", e.toString());
				e.printStackTrace();
			}
		System.out.println(emp);
		return emp;
	}
	
	public JSONObject changeSalary(String designationID, String designationSalary) {
		data = new JSONArray() ;
		emp = new JSONObject();
		try {
			connection= dataSource.getConnection();
			cs = connection.prepareCall("{call changeSalary(?,?)}");
			cs.setString(1, designationID);
			cs.setString(2, designationSalary);
			int a=cs.executeUpdate();
			System.out.println(a);
			if(a!=0) {
				emp.put("msg", "Success");
				}
			}
			catch(Exception e) {
				emp.put("Exception", e.toString());
				e.printStackTrace();
			}
		System.out.println(emp);
		return emp;
	}
	
	public JSONObject deleteDesignation(String designationID) {
		data = new JSONArray() ;
		emp = new JSONObject();
		try {
			connection= dataSource.getConnection();
			cs = connection.prepareCall("{call deleteDesignation(?)}");
			cs.setString(1, designationID);
			int a=cs.executeUpdate();
			System.out.println(a);
			if(a!=0) {
				emp.put("msg", "Success");
				}
			}
			catch(Exception e) {
				emp.put("Exception", e.toString());
				e.printStackTrace();
			}
		System.out.println(emp);
		return emp;
	}

}
