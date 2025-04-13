package com.hexaware.easypay.customvalidator;

import com.hexaware.easypay.model.SalaryDetails;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class SalaryDetailsValidator implements ConstraintValidator<SalaryDetailsValidation, SalaryDetails> {
    @Override
    public boolean isValid(SalaryDetails salary, ConstraintValidatorContext context) {
        if (salary == null) return true;

        boolean isValid = true;

        // Basic salary validation
        if (salary.getBasicSalary().compareTo(new BigDecimal("10000")) < 0) {
            context.buildConstraintViolationWithTemplate("Basic salary cannot be less than ₹10,000")
                  .addPropertyNode("basicSalary")
                  .addConstraintViolation();
            isValid = false;
        }

        // Deductions validation
        BigDecimal totalDeductions = salary.getProvidentFund()
                .add(salary.getProfessionalTax())
                .add(salary.getIncomeTax())
                .add(salary.getInsurancePremium());
                
        if (totalDeductions.compareTo(salary.getGrossSalary()) > 0) {
            context.buildConstraintViolationWithTemplate("Total deductions cannot exceed gross salary")
                  .addPropertyNode("totalDeductions")
                  .addConstraintViolation();
            isValid = false;
        }

        // Bank details validation
        if (salary.getIfscCode() != null && !salary.getIfscCode().matches("^[A-Z]{4}0[A-Z0-9]{6}$")) {
            context.buildConstraintViolationWithTemplate("Invalid IFSC code format")
                  .addPropertyNode("ifscCode")
                  .addConstraintViolation();
            isValid = false;
        }

        if (salary.getAccountNumber() != null && !salary.getAccountNumber().matches("^[0-9]{9,18}$")) {
            context.buildConstraintViolationWithTemplate("Invalid account number format")
                  .addPropertyNode("accountNumber")
                  .addConstraintViolation();
            isValid = false;
        }

        context.disableDefaultConstraintViolation();
        return isValid;
    }
}