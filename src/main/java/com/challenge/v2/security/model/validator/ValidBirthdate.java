package com.challenge.v2.security.model.validator;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Documented
@Constraint(validatedBy = ValidBirthdateValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidBirthdate {
    String message() default "Invalid birthdate (e.g. 30/02/1995)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

class ValidBirthdateValidator implements ConstraintValidator<ValidBirthdate, String> {
	
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        try {
            LocalDate.parse(value, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
