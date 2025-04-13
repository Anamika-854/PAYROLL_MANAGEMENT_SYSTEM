package com.hexaware.easypay.customvalidator;

import com.hexaware.easypay.model.LeaveDetails;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LeaveDetailsValidator implements ConstraintValidator<LeaveDetailsValidation, LeaveDetails> {
    @Override
    public boolean isValid(LeaveDetails leaveDetails, ConstraintValidatorContext context) {
        if (leaveDetails == null) return true;

        boolean isValid = true;

        // Casual Leave validation
        if (leaveDetails.getCasualLeavesUsed() > leaveDetails.getCasualLeavesTotal()) {
            context.buildConstraintViolationWithTemplate("Casual leaves used cannot exceed total casual leaves")
                  .addPropertyNode("casualLeavesUsed")
                  .addConstraintViolation();
            isValid = false;
        }

        // Sick Leave validation
        if (leaveDetails.getSickLeavesUsed() > leaveDetails.getSickLeavesTotal()) {
            context.buildConstraintViolationWithTemplate("Sick leaves used cannot exceed total sick leaves")
                  .addPropertyNode("sickLeavesUsed")
                  .addConstraintViolation();
            isValid = false;
        }

        // Paid Leave validation
        if (leaveDetails.getPaidLeavesUsed() > leaveDetails.getPaidLeavesTotal()) {
            context.buildConstraintViolationWithTemplate("Paid leaves used cannot exceed total paid leaves")
                  .addPropertyNode("paidLeavesUsed")
                  .addConstraintViolation();
            isValid = false;
        }

        // Validate carry forward leaves
        if (leaveDetails.getCarryForwardLeavesUsed() > leaveDetails.getCarryForwardLeavesTotal()) {
            context.buildConstraintViolationWithTemplate("Carry forward leaves used cannot exceed total carry forward leaves")
                  .addPropertyNode("carryForwardLeavesUsed")
                  .addConstraintViolation();
            isValid = false;
        }

        // Validate maximum carry forward limit
        if (leaveDetails.getCarryForwardLeavesTotal() > 30) {
            context.buildConstraintViolationWithTemplate("Carry forward leaves cannot exceed 30 days")
                  .addPropertyNode("carryForwardLeavesTotal")
                  .addConstraintViolation();
            isValid = false;
        }

        context.disableDefaultConstraintViolation();
        return isValid;
    }
}