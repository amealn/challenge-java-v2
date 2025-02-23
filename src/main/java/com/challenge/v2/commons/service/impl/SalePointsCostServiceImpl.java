package com.challenge.v2.commons.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.v2.commons.dao.cache.CacheService;
import com.challenge.v2.commons.model.SalePointsCost;
import com.challenge.v2.commons.service.SalePointsCostService;

@Service
public class SalePointsCostServiceImpl implements SalePointsCostService {
	
	@Autowired
	private CacheService cacheService;
	
	@Override
	public SalePointsCost saveCostBetweenSalePoints() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteCostBetweenSalePoints() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SalePointsCost> getAllCostsToSalePoint() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SalePointsCost getMinimumCostToSalePoint() {
		// TODO Auto-generated method stub
		return null;
	}

}
