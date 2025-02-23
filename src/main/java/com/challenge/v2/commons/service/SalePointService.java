package com.challenge.v2.commons.service;

import java.util.List;

import com.challenge.v2.commons.model.SalePoint;

public interface SalePointService {
	
	public List<SalePoint> getAllSalePoints();
	
    public SalePoint saveSalePoint(SalePoint salePoint);	
	
    public SalePoint updateSalePoint(SalePoint salePoint);	
	
    public Boolean deleteSalePoint(Integer salePoint);   
    

}
