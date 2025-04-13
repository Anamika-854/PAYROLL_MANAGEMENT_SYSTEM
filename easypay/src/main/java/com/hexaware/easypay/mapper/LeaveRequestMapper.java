package com.hexaware.easypay.mapper;

import com.hexaware.easypay.dto.LeaveRequestDTO;
import com.hexaware.easypay.model.LeaveRequest;

public class LeaveRequestMapper {
    public static LeaveRequestDTO mapToLeaveRequestDTO(LeaveRequest leaveRequest) {
        LeaveRequestDTO dto = new LeaveRequestDTO();
        dto.setId(leaveRequest.getId());
        dto.setEmployeeId(leaveRequest.getEmployee().getId());
        dto.setStartDate(leaveRequest.getStartDate());
        dto.setEndDate(leaveRequest.getEndDate());
        dto.setLeaveType(leaveRequest.getLeaveType());
        dto.setReason(leaveRequest.getReason());
        dto.setStatus(leaveRequest.getStatus());
        return dto;
    }

    public static LeaveRequest mapToLeaveRequest(LeaveRequestDTO dto) {
        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setStartDate(dto.getStartDate());
        leaveRequest.setEndDate(dto.getEndDate());
        leaveRequest.setLeaveType(dto.getLeaveType());
        leaveRequest.setReason(dto.getReason());
        leaveRequest.setStatus(dto.getStatus());
        return leaveRequest;
    }
}