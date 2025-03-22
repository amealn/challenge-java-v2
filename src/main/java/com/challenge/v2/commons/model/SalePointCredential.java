package com.challenge.v2.commons.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.challenge.v2.commons.model.validator.ReceptionDateNotAllowedCheck;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Document(collection = "acreditacionesV2")
@ReceptionDateNotAllowedCheck
public class SalePointCredential {
	
	@MongoId
	private String salePointId;
	
	@NotBlank(message = "Sale point name cannot be null")
	@Size(min = 1, max = 50, message = "Sale point name must be between 1 and 50 characters")
	private String salePointName;
	
	@PositiveOrZero(message = "Amount must be zero or a positive number")
	@DecimalMin(value = "0.00", message = "Amount must be greater than or equal to 0.00")
    @Digits(integer = Integer.MAX_VALUE, fraction = 2, message = "Amount must have up to 10 integer digits and 2 fractional digits")
	private Double amount;
	
	private Date receptionDate;
	
	public SalePointCredential() {}	

	public SalePointCredential(String salePointId, String salePointName, Double amount, Date receptionDate) {
		super();
		this.salePointId = salePointId;
		this.salePointName = salePointName;
		this.amount = amount;
		this.receptionDate = receptionDate;
	}

	public String getSalePointId() {
		return salePointId;
	}

	public void setSalePointId(String salePointId) {
		this.salePointId = salePointId;
	}

	public String getSalePointName() {
		return salePointName;
	}

	public void setSalePointName(String salePointName) {
		this.salePointName = salePointName;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getReceptionDate() {
		return receptionDate;
	}

	public void setReceptionDate(Date receptionDate) {
		this.receptionDate = receptionDate;
	}	

}
