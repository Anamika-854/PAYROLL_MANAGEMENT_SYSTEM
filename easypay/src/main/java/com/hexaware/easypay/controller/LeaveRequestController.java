package com.hexaware.easypay.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.easypay.customexceptions.ResourceNotFoundException;
import com.hexaware.easypay.dto.LeaveRequestDTO;
import com.hexaware.easypay.service.ILeaveRequestService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/leave-requests")
public class LeaveRequestController {

	@Autowired
    private ILeaveRequestService leaveRequestService;
	
	@GetMapping
    public ResponseEntity<List<LeaveRequestDTO>> getAllLeaveRequests() {
        return ResponseEntity.ok(leaveRequestService.getAllLeaveRequests());
    }

	@PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER')")
    @PostMapping
    public ResponseEntity<LeaveRequestDTO> createLeaveRequest(@Valid @RequestBody LeaveRequestDTO leaveRequestDTO) 
            throws ResourceNotFoundException {
        return new ResponseEntity<>(leaveRequestService.createLeaveRequest(leaveRequestDTO), 
                HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeaveRequestDTO> getLeaveRequestById(@PathVariable Long id) 
            throws ResourceNotFoundException {
        return ResponseEntity.ok(leaveRequestService.getLeaveRequestById(id));
    }

    @PreAuthorize("#employeeId == authentication.principal.id")
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<LeaveRequestDTO>> getLeaveRequestsByEmployee(
            @PathVariable Long employeeId) {
        return ResponseEntity.ok(leaveRequestService.getLeaveRequestsByEmployee(employeeId));
    }

    @GetMapping("/manager/{managerId}")
    public ResponseEntity<List<LeaveRequestDTO>> getLeaveRequestsByManager(
            @PathVariable Long managerId) {
        return ResponseEntity.ok(leaveRequestService.getLeaveRequestsByManager(managerId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<LeaveRequestDTO>> getLeaveRequestsByStatus(
            @PathVariable String status) {
        return ResponseEntity.ok(leaveRequestService.getLeaveRequestsByStatus(status));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeaveRequestDTO> updateLeaveRequest(
            @PathVariable Long id,
            @Valid @RequestBody LeaveRequestDTO leaveRequestDTO) throws ResourceNotFoundException {
        return ResponseEntity.ok(leaveRequestService.updateLeaveRequest(id, leaveRequestDTO));
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{id}/approve")
    public ResponseEntity<String> approveLeaveRequest(
            @PathVariable Long id,
            @RequestParam Long approverId) throws ResourceNotFoundException {
        leaveRequestService.approveLeaveRequest(id, approverId);
        return ResponseEntity.ok("Leave request approved successfully");
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<String> rejectLeaveRequest(
            @PathVariable Long id,
            @RequestParam Long approverId) throws ResourceNotFoundException {
        leaveRequestService.rejectLeaveRequest(id, approverId);
        return ResponseEntity.ok("Leave request rejected successfully");
    }
    
    @GetMapping("/test")
    public String test() {
        return "LeaveRequest Controller is working!";
    }
}