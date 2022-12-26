package com.rtms;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Response {
	
	@JsonInclude(Include.NON_NULL)
	private Map <String, Object>  data;
	
	Response(){
		
	}
	
	public Response(Map<String, Object> data) {
		super();
		this.data = data;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
	
}
