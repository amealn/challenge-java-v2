package com.challenge.v2.commons.model;

public class SalePoint {
	
	private Integer id;
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
