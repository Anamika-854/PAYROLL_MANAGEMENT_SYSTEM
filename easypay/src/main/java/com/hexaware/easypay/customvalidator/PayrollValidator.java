package com.hexaware.easypay.customvalidator;

import java.util.Arrays;
import java.util.List;

import com.hexaware.easypay.model.Payroll;
import com.hexaware.easypay.model.PayrollStatus;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PayrollValidator implements ConstraintValidator<PayrollValidation, Payroll> {
    private static final List<String> VALID_STATUSES = Arrays.asList(
        "DRAFT", "PROCESSED", "APPROVED", "PAID"
    );

    @Override
    public boolean isValid(Payroll payroll, ConstraintValidatorContext context) {
        if (payroll == null) return true;

        boolean isValid = true;

        // Status validation
        if (payroll.getStatus() == null) {
            context.buildConstraintViolationWithTemplate("Payroll status cannot be null")
                  .addPropertyNode("status")
                  .addConstraintViolation();
            isValid = false;
        }

        // Pay period validation
        if (payroll.getPayPeriodStart() != null && payroll.getPayPeriodEnd() != null) {
            if (payroll.getPayPeriodEnd().isBefore(payroll.getPayPeriodStart())) {
                context.buildConstraintViolationWithTemplate("Pay period end date must be after start date")
                      .addPropertyNode("payPeriodEnd")
                      .addConstraintViolation();
                isValid = false;
            }
        }

        // Remove the net salary validation since it's calculated
        // The old validation was preventing the processing of payroll

        context.disableDefaultConstraintViolation();
        return isValid;
    }
}