package com.hexaware.easypay.customvalidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = LeaveRequestValidator.class)
public @interface LeaveRequestValidation {
    String message() default "Invalid leave request";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}