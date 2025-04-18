package com.challenge.v2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.v2.commons.model.SalePoint;
import com.challenge.v2.commons.service.GenericSalePointService;
import com.challenge.v2.controllers.model.Response;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping(path = "/salePoint", produces = MediaType.APPLICATION_JSON_VALUE)
public class SalePointController {
	
	@Autowired
	@Qualifier("salePointService")
	private GenericSalePointService<SalePoint, Integer> salePointService;
	
	@GetMapping(path = "/getAllSalePoints")
	@Operation(summary = "Get all sale points")
    public Response<List<SalePoint>> getAllSalePoints() {
		return new Response<>(salePointService.getAll());
    }
	
	@PostMapping(path = "/saveSalePoint")
	@Operation(summary = "Saves a sale point")
    public Response<SalePoint> saveSalePoint(@Valid @RequestBody SalePoint salePoint) {
        return new Response<>(salePointService.save(salePoint));
    }
	
	@PostMapping(path = "/updateSalePoint")
	@Operation(summary = "Updates a sale point")
    public Response<SalePoint> updateSalePoint(@Valid @RequestBody SalePoint salePoint) {
        return new Response<>(salePointService.update(salePoint));
    }
	
	@PostMapping(path = "/deleteSalePoint")
	@Operation(summary = "Deletes a sale point")
    public Response<Boolean> deleteSalePoint(@NotNull @RequestBody Integer id) {
        return new Response<>(salePointService.delete(id));
    }

}
