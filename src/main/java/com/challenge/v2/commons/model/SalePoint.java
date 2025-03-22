package com.challenge.v2.commons.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SalePoint {
	
	private Integer id;
	
	@NotBlank(message = "Sale point name cannot be null")
	@Size(min = 1, max = 50, message = "Sale point name must be between 1 and 50 characters")
	private String name;
	
	public SalePoint(){}
	
	public SalePoint(Integer id, String name){
		this.id = id;
		this.name = name;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}	

}
