package com.challenge.v2.commons.model.validator;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = ZeroCostValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ZeroCostForSameSalePointsCheck {
	
	String message() default "Cost must be 0 when origin and destination are the same";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
