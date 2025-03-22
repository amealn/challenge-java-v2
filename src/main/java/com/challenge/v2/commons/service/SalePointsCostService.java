package com.challenge.v2.commons.service;

import java.util.List;

import com.challenge.v2.commons.model.SalePointsCost;

public interface SalePointsCostService {
	
	public Boolean saveCostBetweenSalePoints(SalePointsCost request);
	
    public Boolean deleteCostBetweenSalePoints(SalePointsCost request);
	
    public List<SalePointsCost> getAllCostsToSalePoint(Integer originSalePointId);
	
    public String getMinimumCostToSalePoint(SalePointsCost request);

}
