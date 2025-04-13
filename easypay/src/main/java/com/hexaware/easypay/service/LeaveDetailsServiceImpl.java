package com.hexaware.easypay.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hexaware.easypay.customexceptions.ResourceNotFoundException;
import com.hexaware.easypay.dto.LeaveDetailsDTO;
import com.hexaware.easypay.model.Employee;
import com.hexaware.easypay.model.LeaveDetails;
import com.hexaware.easypay.repository.EmployeeRepository;
import com.hexaware.easypay.repository.LeaveDetailsRepository;


@Service
public class LeaveDetailsServiceImpl implements ILeaveDetailsService {
	
	@Autowired
    private LeaveDetailsRepository leaveDetailsRepository;
	@Autowired
    private EmployeeRepository employeeRepository;
	@Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public LeaveDetailsDTO createLeaveDetails(LeaveDetailsDTO leaveDetailsDTO) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(leaveDetailsDTO.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", leaveDetailsDTO.getEmployeeId()));

        if (leaveDetailsRepository.findByEmployeeId(leaveDetailsDTO.getEmployeeId()).isPresent()) {
            throw new IllegalStateException("Leave details already exist for this employee");
        }

        LeaveDetails leaveDetails = new LeaveDetails();
        leaveDetails.setEmployee(employee);
        // Default values are already set in entity class

        LeaveDetails savedDetails = leaveDetailsRepository.save(leaveDetails);
        return modelMapper.map(savedDetails, LeaveDetailsDTO.class);
    }

    @Override
    @Transactional
    public LeaveDetailsDTO updateLeaveDetails(Long id, LeaveDetailsDTO leaveDetailsDTO) throws ResourceNotFoundException {
        LeaveDetails existingDetails = leaveDetailsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveDetails", "id", id));

        // Update only allowed fields
        if(leaveDetailsDTO.getCompensatoryLeavesEarned() != null) {
            existingDetails.setCompensatoryLeavesEarned(leaveDetailsDTO.getCompensatoryLeavesEarned());
        }

        LeaveDetails updatedDetails = leaveDetailsRepository.save(existingDetails);
        return modelMapper.map(updatedDetails, LeaveDetailsDTO.class);
    }

    @Override
    public LeaveDetailsDTO getLeaveDetailsByEmployeeId(Long employeeId) throws ResourceNotFoundException {
        LeaveDetails leaveDetails = leaveDetailsRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveDetails", "employeeId", employeeId));
        return modelMapper.map(leaveDetails, LeaveDetailsDTO.class);
    }

    @Override
    @Transactional
    public void processYearEndLeaveCarryForward(Long employeeId) throws ResourceNotFoundException {
        LeaveDetails leaveDetails = leaveDetailsRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveDetails", "employeeId", employeeId));

        // Calculate unused paid leaves
        int unusedPaidLeaves = leaveDetails.getPaidLeavesTotal() - leaveDetails.getPaidLeavesUsed();
        int carryForwardLeaves = Math.min(unusedPaidLeaves, 30); // Maximum 30 days

        // Reset annual leave quotas
        leaveDetails.setPaidLeavesTotal(15);
        leaveDetails.setPaidLeavesUsed(0);
        leaveDetails.setSickLeavesTotal(15);
        leaveDetails.setSickLeavesUsed(0);
        leaveDetails.setCasualLeavesTotal(12);
        leaveDetails.setCasualLeavesUsed(0);

        // Set carry forward
        leaveDetails.setCarryForwardLeavesTotal(carryForwardLeaves);
        leaveDetails.setCarryForwardLeavesUsed(0);

        leaveDetailsRepository.save(leaveDetails);
    }

    @Override
    public boolean checkLeaveAvailability(Long employeeId, String leaveType, int days) throws ResourceNotFoundException {
        LeaveDetails leaveDetails = leaveDetailsRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveDetails", "employeeId", employeeId));

        return switch (leaveType.toUpperCase()) {
            case "CASUAL" -> (leaveDetails.getCasualLeavesTotal() - leaveDetails.getCasualLeavesUsed()) >= days;
            case "SICK" -> (leaveDetails.getSickLeavesTotal() - leaveDetails.getSickLeavesUsed()) >= days;
            case "PAID" -> {
                int paidLeaveBalance = leaveDetails.getPaidLeavesTotal() - leaveDetails.getPaidLeavesUsed();
                int carryForwardBalance = leaveDetails.getCarryForwardLeavesTotal() - leaveDetails.getCarryForwardLeavesUsed();
                yield (paidLeaveBalance + carryForwardBalance) >= days;
            }
            case "MATERNITY" -> (leaveDetails.getMaternityLeavesTotal() - leaveDetails.getMaternityLeavesUsed()) >= days;
            case "PATERNITY" -> (leaveDetails.getPaternityLeavesTotal() - leaveDetails.getPaternityLeavesUsed()) >= days;
            case "MARRIAGE" -> (leaveDetails.getMarriageLeavesTotal() - leaveDetails.getMarriageLeavesUsed()) >= days;
            case "COMPENSATORY" -> (leaveDetails.getCompensatoryLeavesEarned() - leaveDetails.getCompensatoryLeavesUsed()) >= days;
            default -> throw new IllegalArgumentException("Invalid leave type: " + leaveType);
        };
    }

    @Override
    @Transactional
    public void deductLeaves(Long employeeId, String leaveType, int days) throws ResourceNotFoundException {
        LeaveDetails leaveDetails = leaveDetailsRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveDetails", "employeeId", employeeId));

        if (!checkLeaveAvailability(employeeId, leaveType, days)) {
            throw new IllegalStateException("Insufficient leave balance");
        }

        switch (leaveType.toUpperCase()) {
            case "CASUAL" -> leaveDetails.setCasualLeavesUsed(leaveDetails.getCasualLeavesUsed() + days);
            case "SICK" -> leaveDetails.setSickLeavesUsed(leaveDetails.getSickLeavesUsed() + days);
            case "PAID" -> deductPaidLeaves(leaveDetails, days);
            case "MATERNITY" -> leaveDetails.setMaternityLeavesUsed(leaveDetails.getMaternityLeavesUsed() + days);
            case "PATERNITY" -> leaveDetails.setPaternityLeavesUsed(leaveDetails.getPaternityLeavesUsed() + days);
            case "MARRIAGE" -> leaveDetails.setMarriageLeavesUsed(leaveDetails.getMarriageLeavesUsed() + days);
            case "COMPENSATORY" -> leaveDetails.setCompensatoryLeavesUsed(leaveDetails.getCompensatoryLeavesUsed() + days);
            default -> throw new IllegalArgumentException("Invalid leave type: " + leaveType);
        }

        leaveDetailsRepository.save(leaveDetails);
    }

    private void deductPaidLeaves(LeaveDetails leaveDetails, int days) {
        int carryForwardAvailable = leaveDetails.getCarryForwardLeavesTotal() - leaveDetails.getCarryForwardLeavesUsed();
        
        if (carryForwardAvailable > 0) {
            int carryForwardToUse = Math.min(carryForwardAvailable, days);
            leaveDetails.setCarryForwardLeavesUsed(leaveDetails.getCarryForwardLeavesUsed() + carryForwardToUse);
            days -= carryForwardToUse;
        }

        if (days > 0) {
            leaveDetails.setPaidLeavesUsed(leaveDetails.getPaidLeavesUsed() + days);
        }
    }
}