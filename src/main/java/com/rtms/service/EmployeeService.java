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
	
	//Employee
	
	public JSONObject addEmployee(String firstName, String lastName, String address, String city, String designationID, String phoneNo) {
		data = new JSONArray() ;
		emp = new JSONObject();
		try {
			connection= dataSource.getConnection();
			cs = connection.prepareCall("{call addEmployee(?,?,?,?,?,?)}");
			cs.setString(1, firstName);
			cs.setString(2, lastName);
			cs.setString(3, address);
			cs.setString(4, city);
			cs.setString(5, designationID);
			cs.setString(6, phoneNo);
			
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
	
	public JSONObject getEmployees() {
		data = new JSONArray() ;
		emp = new JSONObject();
		try {
			connection= dataSource.getConnection();
			cs = connection.prepareCall("{call getEmployees()}");
			rs = cs.executeQuery();
			while(rs.next()) {
				emp.put("msg", "Success");
				JSONObject result= new JSONObject();
				result.put("ID",rs.getString("employeeID"));
				result.put("firstName",rs.getString("firstName"));
				result.put("LastName", rs.getString("LastName"));
				result.put("address", rs.getString("address"));
				result.put("city", rs.getString("city"));
				result.put("phoneNo", rs.getString("phoneNo"));
				result.put("designationName", rs.getString("designationName"));
				data.put(result);
				}
			emp.put("Employees", data);
			
			}
			catch(Exception e) {
				emp.put("Exception", e.toString());
				e.printStackTrace();
			}
		System.out.println(emp);
		return emp;	
	}
	
	public JSONObject getEmployeeByID(String id) {
		data = new JSONArray() ;
		emp = new JSONObject();
		try {
			connection= dataSource.getConnection();
			cs = connection.prepareCall("{call getEmployeeByID(?)}");
			cs.setString(1, id);
			rs = cs.executeQuery();
			while(rs.next()) {
				emp.put("msg", "Success");
				JSONObject result= new JSONObject();
				result.put("ID",rs.getString("employeeID"));
				result.put("firstName",rs.getString("firstName"));
				result.put("LastName", rs.getString("LastName"));
				result.put("address", rs.getString("address"));
				result.put("city", rs.getString("city"));
				result.put("phoneNo", rs.getString("phoneNo"));
				result.put("designationName", rs.getString("designationName"));
				result.put("Salary",rs.getString("designationSalary"));
				emp.put("Employee", result);
				}
			}
			catch(Exception e) {
				emp.put("Exception", e.toString());
				e.printStackTrace();
			}
		System.out.println(emp);
		return emp;	
	}
	
	public JSONObject updateEmployee(String employeeID,String firstName, String lastName, String address, String city,String phoneNo) {
		data = new JSONArray() ;
		emp = new JSONObject();

		try {
			connection= dataSource.getConnection();
			cs = connection.prepareCall("{call updateEmployee(?,?,?,?,?,?)}");
			cs.setString(1, employeeID);
			cs.setString(2, firstName);
			cs.setString(3, lastName);
			cs.setString(4, address);
			cs.setString(5, city);
			cs.setString(6, phoneNo);
			
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
	
	public JSONObject deleteEmployee(String employeeID) {
		data = new JSONArray() ;
		emp = new JSONObject();
		try {
			connection= dataSource.getConnection();
			cs = connection.prepareCall("{call deleteEmployee(?)}");
			cs.setString(1, employeeID);
			int a=cs.executeUpdate();
			System.out.println(employeeID);
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
	
	
	
	//Designation
	
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
	
	public JSONObject getDesignation() {
		data = new JSONArray() ;
		emp = new JSONObject();
		try {
			connection= dataSource.getConnection();
			cs = connection.prepareCall("{call getDesignation()}");
			rs = cs.executeQuery();
			while(rs.next()) {
				emp.put("msg", "Success");
				JSONObject result= new JSONObject();
				result.put("designationID",rs.getString("designationID"));
				result.put("designationName", rs.getString("designationName"));
				result.put("designationSalary", rs.getString("designationSalary"));
				data.put(result);
				}
			emp.put("Designations", data);
			
			}
			catch(Exception e) {
				emp.put("Exception", e.toString());
				e.printStackTrace();
			}
		System.out.println(emp);
		return emp;	
	}

}
