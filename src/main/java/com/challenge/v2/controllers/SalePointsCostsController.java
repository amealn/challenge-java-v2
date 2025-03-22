package com.challenge.v2.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.v2.commons.model.SalePointsCost;
import com.challenge.v2.commons.service.SalePointsCostService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/salePointsCosts", produces = MediaType.APPLICATION_JSON_VALUE)
public class SalePointsCostsController {
	
	@Autowired
	private SalePointsCostService salePointsCostService;	

	
	@PostMapping(path = "/saveCostBetweenSalePoints")
	@Operation(summary = "Saves a new cost between sale points")
    public Boolean saveCostBetweenSalePoints(@Valid @RequestBody SalePointsCost request) {
        return salePointsCostService.saveCostBetweenSalePoints(request);
    }
	
	@PostMapping(path = "/deleteCostBetweenSalePoints")
	@Operation(summary = "Deletes a cost between sale points")
    public Boolean deleteCostBetweenSalePoints(@Valid @RequestBody SalePointsCost request) {
        return salePointsCostService.deleteCostBetweenSalePoints(request);
    }
	
	@PostMapping(path = "/getAllCostsToSalePoint")
	@Operation(summary = "Get all costs between sale points")
    public List<SalePointsCost> getAllCostsToSalePoint(@RequestBody Integer originSalePointId) {
        return salePointsCostService.getAllCostsToSalePoint(originSalePointId);
    }
	
	@PostMapping(path = "/getMinimumCostToSalePoint")
	@Operation(summary = "Get minimum cost between sale points")
    public ResponseEntity<Map<String, String>> getMinimumCostToSalePoint(@Valid @RequestBody SalePointsCost request) {
		String result = salePointsCostService.getMinimumCostToSalePoint(request);
		Map<String, String> response = new HashMap<>();
        response.put("Result: ", result);
        return ResponseEntity.ok(response);
    }

}
