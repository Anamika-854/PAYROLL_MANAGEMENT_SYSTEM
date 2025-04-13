package com.hexaware.easypay.customvalidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = EmployeeValidator.class)
public @interface EmployeeValidation {
    String message() default "Invalid employee data";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
