package com.challenge.v2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.v2.commons.model.SalePoint;
import com.challenge.v2.commons.model.SalePointsCost;
import com.challenge.v2.commons.service.SalePointService;
import com.challenge.v2.commons.service.SalePointsCostService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(path = "/salePoint", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppController {
	
	@Autowired
	private SalePointService salePointService;
	
	@Autowired
	private SalePointsCostService salePointsCostService;
	
	@GetMapping(path = "/getAllSalePoints")
	@Operation(summary = "Get all sale points")
    public List<SalePoint> getAllSalePoints() {
        return salePointService.getAllSalePoints();
    }
	
	@PostMapping(path = "/saveSalePoint")
	@Operation(summary = "Saves a sale point")
    public SalePoint saveSalePoint(@RequestBody SalePoint salePoint) {
        return salePointService.saveSalePoint(salePoint);
    }
	
	@PostMapping(path = "/updateSalePoint")
	@Operation(summary = "Updates a sale point")
    public SalePoint updateSalePoint(@RequestBody SalePoint salePoint) {
        return salePointService.updateSalePoint(salePoint);
    }
	
	@PostMapping(path = "/deleteSalePoint")
	@Operation(summary = "Deletes a sale point")
    public Boolean deleteSalePoint(@RequestBody SalePoint salePoint) {
        return salePointService.deleteSalePoint(salePoint.getId());
    }
	
	@PostMapping(path = "/saveCostBetweenSalePoints")
	@Operation(summary = "Saves a new cost between sale points")
    public SalePointsCost saveCostBetweenSalePoints() {
        return salePointsCostService.saveCostBetweenSalePoints();
    }
	
	@PostMapping(path = "/deleteCostBetweenSalePoints")
	@Operation(summary = "Deletes a cost between sale points")
    public Boolean deleteCostBetweenSalePoints() {
        return salePointsCostService.deleteCostBetweenSalePoints();
    }
	
	@PostMapping(path = "/getAllCostsToSalePoint")
	@Operation(summary = "Get all costs between sale points")
    public List<SalePointsCost> getAllCostsToSalePoint() {
        return salePointsCostService.getAllCostsToSalePoint();
    }
	
	@PostMapping(path = "/getMinimumCostToSalePoint")
	@Operation(summary = "Get minimum cost between sale points")
    public SalePointsCost getMinimumCostToSalePoint() {
        return salePointsCostService.getMinimumCostToSalePoint();
    }
	
	
	
	
	//REVISAR ESTOS
	
	@PostMapping(path = "/saveAcreditacion")
    public Boolean saveAcreditacion() {
        return salePointsCostService.deleteCostBetweenSalePoints();
    }
	
	@PostMapping(path = "/getAcreditacion")
    public Boolean getAcreditacion() {
        return salePointsCostService.deleteCostBetweenSalePoints();
    }

}
