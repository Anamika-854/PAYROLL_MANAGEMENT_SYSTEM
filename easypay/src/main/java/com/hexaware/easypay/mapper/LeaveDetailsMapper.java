package com.hexaware.easypay.mapper;

import com.hexaware.easypay.dto.LeaveDetailsDTO;
import com.hexaware.easypay.model.LeaveDetails;

public class LeaveDetailsMapper {
    
    public static LeaveDetailsDTO mapToDTO(LeaveDetails leaveDetails) {
        LeaveDetailsDTO dto = new LeaveDetailsDTO();
        dto.setId(leaveDetails.getId());
        dto.setEmployeeId(leaveDetails.getEmployee().getId());
        dto.setCasualLeavesTotal(leaveDetails.getCasualLeavesTotal());
        dto.setCasualLeavesUsed(leaveDetails.getCasualLeavesUsed());
        dto.setSickLeavesTotal(leaveDetails.getSickLeavesTotal());
        dto.setSickLeavesUsed(leaveDetails.getSickLeavesUsed());
        dto.setPaidLeavesTotal(leaveDetails.getPaidLeavesTotal());
        dto.setPaidLeavesUsed(leaveDetails.getPaidLeavesUsed());
        dto.setCarryForwardLeavesTotal(leaveDetails.getCarryForwardLeavesTotal());
        dto.setCarryForwardLeavesUsed(leaveDetails.getCarryForwardLeavesUsed());
        dto.setMaternityLeavesTotal(leaveDetails.getMaternityLeavesTotal());
        dto.setMaternityLeavesUsed(leaveDetails.getMaternityLeavesUsed());
        dto.setPaternityLeavesTotal(leaveDetails.getPaternityLeavesTotal());
        dto.setPaternityLeavesUsed(leaveDetails.getPaternityLeavesUsed());
        dto.setMarriageLeavesTotal(leaveDetails.getMarriageLeavesTotal());
        dto.setMarriageLeavesUsed(leaveDetails.getMarriageLeavesUsed());
        dto.setCompensatoryLeavesEarned(leaveDetails.getCompensatoryLeavesEarned());
        dto.setCompensatoryLeavesUsed(leaveDetails.getCompensatoryLeavesUsed());
        return dto;
    }

    public static LeaveDetails mapToEntity(LeaveDetailsDTO dto) {
        LeaveDetails leaveDetails = new LeaveDetails();
        leaveDetails.setCasualLeavesTotal(dto.getCasualLeavesTotal());
        leaveDetails.setSickLeavesTotal(dto.getSickLeavesTotal());
        leaveDetails.setPaidLeavesTotal(dto.getPaidLeavesTotal());
        leaveDetails.setMaternityLeavesTotal(dto.getMaternityLeavesTotal());
        leaveDetails.setPaternityLeavesTotal(dto.getPaternityLeavesTotal());
        leaveDetails.setMarriageLeavesTotal(dto.getMarriageLeavesTotal());
        leaveDetails.setCompensatoryLeavesEarned(dto.getCompensatoryLeavesEarned());
        return leaveDetails;
    }
}