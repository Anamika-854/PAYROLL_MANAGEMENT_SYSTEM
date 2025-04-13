package com.hexaware.easypay.customvalidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PayrollValidator.class)
public @interface PayrollValidation {
    String message() default "Invalid payroll data";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}