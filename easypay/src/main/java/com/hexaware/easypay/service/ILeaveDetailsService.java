package com.hexaware.easypay.service;

import com.hexaware.easypay.dto.LeaveDetailsDTO;
import com.hexaware.easypay.customexceptions.ResourceNotFoundException;

public interface ILeaveDetailsService {
    LeaveDetailsDTO createLeaveDetails(LeaveDetailsDTO leaveDetailsDTO) throws ResourceNotFoundException;
    LeaveDetailsDTO updateLeaveDetails(Long id, LeaveDetailsDTO leaveDetailsDTO) throws ResourceNotFoundException;
    LeaveDetailsDTO getLeaveDetailsByEmployeeId(Long employeeId) throws ResourceNotFoundException;
    void processYearEndLeaveCarryForward(Long employeeId) throws ResourceNotFoundException;
    boolean checkLeaveAvailability(Long employeeId, String leaveType, int days) throws ResourceNotFoundException;
    void deductLeaves(Long employeeId, String leaveType, int days) throws ResourceNotFoundException;
}
