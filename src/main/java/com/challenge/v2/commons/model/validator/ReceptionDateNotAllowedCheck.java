package com.challenge.v2.commons.model.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ReceptionDateNotAllowedValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReceptionDateNotAllowedCheck {

    String message() default "Reception date must not be provided in the request";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
