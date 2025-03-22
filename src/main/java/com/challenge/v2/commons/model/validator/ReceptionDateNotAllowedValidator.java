package com.challenge.v2.commons.model.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Date;

import com.challenge.v2.commons.model.SalePointCredential;

public class ReceptionDateNotAllowedValidator implements ConstraintValidator<ReceptionDateNotAllowedCheck, SalePointCredential> {

    @Override
    public boolean isValid(SalePointCredential value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        Date receptionDate = value.getReceptionDate();
        return receptionDate == null;
    }
}
