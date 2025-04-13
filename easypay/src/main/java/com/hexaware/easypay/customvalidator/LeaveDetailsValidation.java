package com.hexaware.easypay.customvalidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = LeaveDetailsValidator.class)
public @interface LeaveDetailsValidation {
    String message() default "Invalid leave details";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}