package com.hexaware.easypay.service;

import com.hexaware.easypay.customexceptions.ResourceNotFoundException;
import com.hexaware.easypay.dto.LeaveRequestDTO;
import com.hexaware.easypay.model.Employee;
import com.hexaware.easypay.model.LeaveRequest;
import com.hexaware.easypay.repository.EmployeeRepository;
import com.hexaware.easypay.repository.LeaveRequestRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaveRequestServiceImpl implements ILeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public LeaveRequestServiceImpl(
            LeaveRequestRepository leaveRequestRepository,
            EmployeeRepository employeeRepository,
            ModelMapper modelMapper) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }
    
    @Override
    public List<LeaveRequestDTO> getAllLeaveRequests() {
        List<LeaveRequest> leaveRequests = leaveRequestRepository.findAll();
        return leaveRequests.stream()
                .map(request -> modelMapper.map(request, LeaveRequestDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public LeaveRequestDTO createLeaveRequest(LeaveRequestDTO leaveRequestDTO) 
            throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(leaveRequestDTO.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", leaveRequestDTO.getEmployeeId()));

        LeaveRequest leaveRequest = modelMapper.map(leaveRequestDTO, LeaveRequest.class);
        leaveRequest.setEmployee(employee);
        leaveRequest.setStatus("PENDING");

        LeaveRequest savedLeaveRequest = leaveRequestRepository.save(leaveRequest);
        return modelMapper.map(savedLeaveRequest, LeaveRequestDTO.class);
    }

    @Override
    public LeaveRequestDTO updateLeaveRequest(Long id, LeaveRequestDTO leaveRequestDTO) 
            throws ResourceNotFoundException {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveRequest", "id", id));

        if (!leaveRequest.getStatus().equals("PENDING")) {
            throw new IllegalStateException("Can only update pending leave requests");
        }

        leaveRequest.setStartDate(leaveRequestDTO.getStartDate());
        leaveRequest.setEndDate(leaveRequestDTO.getEndDate());
        leaveRequest.setLeaveType(leaveRequestDTO.getLeaveType());
        leaveRequest.setReason(leaveRequestDTO.getReason());

        LeaveRequest updatedLeaveRequest = leaveRequestRepository.save(leaveRequest);
        return modelMapper.map(updatedLeaveRequest, LeaveRequestDTO.class);
    }

    @Override
    public LeaveRequestDTO getLeaveRequestById(Long id) throws ResourceNotFoundException {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveRequest", "id", id));
        return modelMapper.map(leaveRequest, LeaveRequestDTO.class);
    }

    @Override
    public List<LeaveRequestDTO> getLeaveRequestsByEmployee(Long employeeId) {
        return leaveRequestRepository.findByEmployeeId(employeeId).stream()
                .map(leaveRequest -> modelMapper.map(leaveRequest, LeaveRequestDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<LeaveRequestDTO> getLeaveRequestsByManager(Long managerId) {
        return leaveRequestRepository.findByManagerId(managerId).stream()
                .map(leaveRequest -> modelMapper.map(leaveRequest, LeaveRequestDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<LeaveRequestDTO> getLeaveRequestsByStatus(String status) {
        return leaveRequestRepository.findByStatus(status).stream()
                .map(leaveRequest -> modelMapper.map(leaveRequest, LeaveRequestDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void approveLeaveRequest(Long id, Long approverId) throws ResourceNotFoundException {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveRequest", "id", id));
        
        Employee approver = employeeRepository.findById(approverId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", approverId));

        if (!"PENDING".equals(leaveRequest.getStatus())) {
            throw new IllegalStateException("Can only approve pending leave requests");
        }

        leaveRequest.setStatus("APPROVED");
        leaveRequest.setApprovedBy(approver);
        leaveRequestRepository.save(leaveRequest);
    }

    @Override
    public void rejectLeaveRequest(Long id, Long approverId) throws ResourceNotFoundException {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveRequest", "id", id));
        
        Employee approver = employeeRepository.findById(approverId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", approverId));

        if (!leaveRequest.getStatus().equals("PENDING")) {
            throw new IllegalStateException("Can only reject pending leave requests");
        }

        leaveRequest.setStatus("REJECTED");
        leaveRequest.setApprovedBy(approver);
        leaveRequestRepository.save(leaveRequest);
    }
}