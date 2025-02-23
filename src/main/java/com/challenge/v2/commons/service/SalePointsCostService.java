package com.challenge.v2.commons.service;

import java.util.List;

import com.challenge.v2.commons.model.SalePointsCost;

public interface SalePointsCostService {
	
	public SalePointsCost saveCostBetweenSalePoints();
	
    public Boolean deleteCostBetweenSalePoints();
	
    public List<SalePointsCost> getAllCostsToSalePoint();
	
    public SalePointsCost getMinimumCostToSalePoint();

}
