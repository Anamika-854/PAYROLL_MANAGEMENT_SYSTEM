package com.hexaware.easypay.customvalidator;

import com.hexaware.easypay.model.LeaveRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class LeaveRequestValidator implements ConstraintValidator<LeaveRequestValidation, LeaveRequest> {
    private static final List<String> VALID_STATUSES = Arrays.asList(
        "PENDING", "APPROVED", "REJECTED", "CANCELLED"
    );

    @Override
    public boolean isValid(LeaveRequest request, ConstraintValidatorContext context) {
        if (request == null) return true;

        boolean isValid = true;

        // Date validation
        if (request.getStartDate() != null && request.getEndDate() != null) {
            if (request.getEndDate().isBefore(request.getStartDate())) {
                context.buildConstraintViolationWithTemplate("End date cannot be before start date")
                      .addPropertyNode("endDate")
                      .addConstraintViolation();
                isValid = false;
            }

            if (request.getStartDate().isBefore(LocalDate.now())) {
                context.buildConstraintViolationWithTemplate("Cannot apply leave for past dates")
                      .addPropertyNode("startDate")
                      .addConstraintViolation();
                isValid = false;
            }
        }

        // Status validation
        if (request.getStatus() != null && !VALID_STATUSES.contains(request.getStatus().toUpperCase())) {
            context.buildConstraintViolationWithTemplate("Invalid leave request status")
                  .addPropertyNode("status")
                  .addConstraintViolation();
            isValid = false;
        }

        // Leave type validation
        if (request.getLeaveType() == null || request.getLeaveType().trim().isEmpty()) {
            context.buildConstraintViolationWithTemplate("Leave type cannot be empty")
                  .addPropertyNode("leaveType")
                  .addConstraintViolation();
            isValid = false;
        }

        context.disableDefaultConstraintViolation();
        return isValid;
    }
}