package com.hexaware.easypay.customvalidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = SalaryDetailsValidator.class)
public @interface SalaryDetailsValidation {
    String message() default "Invalid salary details";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}