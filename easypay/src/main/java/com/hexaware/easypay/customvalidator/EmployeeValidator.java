package com.hexaware.easypay.customvalidator;

import com.hexaware.easypay.dto.EmployeeDTO;
import com.hexaware.easypay.model.Employee;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmployeeValidator implements ConstraintValidator<EmployeeValidation, EmployeeDTO> {
    @Override
    public boolean isValid(EmployeeDTO employeeDTO, ConstraintValidatorContext context) {
        boolean isValid = true;

        // Email validation
        if (employeeDTO.getEmail() != null && !employeeDTO.getEmail().isEmpty()) {
            if (!employeeDTO.getEmail().matches("^[A-Za-z0-9+_.-]+@hexaware\\.com$")) {
                context.buildConstraintViolationWithTemplate("Invalid email format. Must be a valid company email")
                      .addPropertyNode("email")
                      .addConstraintViolation();
                isValid = false;
            }
        }

        // Phone validation
        if (employeeDTO.getPhoneNumber() != null && !employeeDTO.getPhoneNumber().isEmpty()) {
            if (!employeeDTO.getPhoneNumber().matches("^[0-9]{10}$")) {
                context.buildConstraintViolationWithTemplate("Invalid phone number format. Must be 10 digits")
                      .addPropertyNode("phoneNumber")
                      .addConstraintViolation();
                isValid = false;
            }
        }

        // Add any other employee-specific validations here
        
        context.disableDefaultConstraintViolation(); // Disable default message
        return isValid;
    }
}