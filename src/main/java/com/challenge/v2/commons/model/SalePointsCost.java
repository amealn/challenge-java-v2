package com.challenge.v2.commons.model;

import com.challenge.v2.commons.model.validator.ZeroCostForSameSalePointsCheck;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@ZeroCostForSameSalePointsCheck
public class SalePointsCost {

	@NotNull(message = "Origin ID cannot be null")
	@Positive(message = "Origin ID must be a positive number")
	private Integer originSalePointId;
	
	@NotNull(message = "Destination ID cannot be null")
	@Positive(message = "Destination ID must be a positive number")
	private Integer destinationSalePointId;
	
	@NotNull(message = "Cost cannot be null")
	@PositiveOrZero(message = "Cost must be zero or a positive number")
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
