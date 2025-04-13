package com.hexaware.easypay.service;

import com.hexaware.easypay.customexceptions.ResourceNotFoundException;
import com.hexaware.easypay.dto.LeaveRequestDTO;
import java.util.List;

public interface ILeaveRequestService {
    LeaveRequestDTO createLeaveRequest(LeaveRequestDTO leaveRequestDTO) throws ResourceNotFoundException;
    LeaveRequestDTO updateLeaveRequest(Long id, LeaveRequestDTO leaveRequestDTO) throws ResourceNotFoundException;
    LeaveRequestDTO getLeaveRequestById(Long id) throws ResourceNotFoundException;
    List<LeaveRequestDTO> getLeaveRequestsByEmployee(Long employeeId);
    List<LeaveRequestDTO> getLeaveRequestsByManager(Long managerId);
    List<LeaveRequestDTO> getLeaveRequestsByStatus(String status);
    List<LeaveRequestDTO> getAllLeaveRequests();
    void approveLeaveRequest(Long id, Long approverId) throws ResourceNotFoundException;
    void rejectLeaveRequest(Long id, Long approverId) throws ResourceNotFoundException;
}