package com.challenge.v2.commons.model.validator;

import com.challenge.v2.commons.model.SalePointsCost;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ZeroCostValidator implements ConstraintValidator<ZeroCostForSameSalePointsCheck, SalePointsCost> {

	@Override
	public boolean isValid(SalePointsCost value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		
		if (value.getOriginSalePointId() != null && value.getDestinationSalePointId() != null && value.getCost() != null) {
			if (value.getOriginSalePointId().equals(value.getDestinationSalePointId())) {
				return value.getCost() == 0;
			}
		}

		return true;
	}

}
