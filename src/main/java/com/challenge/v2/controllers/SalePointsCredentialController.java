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

import com.challenge.v2.commons.model.SalePointCredential;
import com.challenge.v2.commons.service.GenericSalePointService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping(path = "/salePointCredential", produces = MediaType.APPLICATION_JSON_VALUE)
public class SalePointsCredentialController {
	
	@Autowired
	@Qualifier("salePointsCredentialService")
	private GenericSalePointService<SalePointCredential, Integer> salePointsCredentialService;	
	
	@GetMapping(path = "/getAllSalePointsCredentials")
	@Operation(summary = "Get all sale points credentials")
    public List<SalePointCredential> getAllSalePointsCredentials() {
        return salePointsCredentialService.getAll();
    }
	
	@PostMapping(path = "/saveSalePointCredential")
	@Operation(summary = "Saves a sale point credential")
    public SalePointCredential saveSalePointCredential(@Valid @RequestBody SalePointCredential salePoint) {
        return salePointsCredentialService.save(salePoint);
    }
	
	@PostMapping(path = "/updateSalePointCredential")
	@Operation(summary = "Updates a sale point credential")
    public SalePointCredential updateSalePointCredential(@Valid @RequestBody SalePointCredential salePoint) {
        return salePointsCredentialService.update(salePoint);
    }
	
	@PostMapping(path = "/deleteSalePointCredential")
	@Operation(summary = "Deletes a sale point credential")
    public Boolean deleteSalePointCredential(@NotNull @RequestBody Integer id) {
        return salePointsCredentialService.delete(id);
    }

}
