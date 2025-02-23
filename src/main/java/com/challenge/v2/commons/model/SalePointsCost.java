package com.challenge.v2.commons.model;

public class SalePointsCost {

	private Integer originSalePointId;
	private Integer destinationSalePointId;
	private Long cost;
	
	public SalePointsCost() {}
	
	public SalePointsCost(Integer originSalePointId, Integer destinationSalePointId, Long cost) {
		super();
		this.originSalePointId = originSalePointId;
		this.destinationSalePointId = destinationSalePointId;
		this.cost = cost;
	}

	public Integer getOriginSalePointId() {
		return originSalePointId;
	}

	public void setOriginSalePointId(Integer originSalePointId) {
		this.originSalePointId = originSalePointId;
	}

	public Integer getDestinationSalePointId() {
		return destinationSalePointId;
	}

	public void setDestinationSalePointId(Integer destinationSalePointId) {
		this.destinationSalePointId = destinationSalePointId;
	}

	public Long getCost() {
		return cost;
	}

	public void setCost(Long cost) {
		this.cost = cost;
	}	

}
