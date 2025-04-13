package com.hexaware.easypay.controller;

import com.hexaware.easypay.customexceptions.ResourceNotFoundException;
import com.hexaware.easypay.dto.LeaveDetailsDTO;
import com.hexaware.easypay.service.ILeaveDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/leave-details")
public class LeaveDetailsController {

    @Autowired
    private ILeaveDetailsService leaveDetailsService;

    @PostMapping
    public ResponseEntity<LeaveDetailsDTO> createLeaveDetails(@Valid @RequestBody LeaveDetailsDTO leaveDetailsDTO) 
            throws ResourceNotFoundException {
        return new ResponseEntity<>(leaveDetailsService.createLeaveDetails(leaveDetailsDTO), 
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeaveDetailsDTO> updateLeaveDetails(
            @PathVariable Long id,
            @Valid @RequestBody LeaveDetailsDTO leaveDetailsDTO) throws ResourceNotFoundException {
        return ResponseEntity.ok(leaveDetailsService.updateLeaveDetails(id, leaveDetailsDTO));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<LeaveDetailsDTO> getLeaveDetailsByEmployeeId(@PathVariable Long employeeId) 
            throws ResourceNotFoundException {
        return ResponseEntity.ok(leaveDetailsService.getLeaveDetailsByEmployeeId(employeeId));
    }

    @PostMapping("/year-end-process/{employeeId}")
    public ResponseEntity<String> processYearEndLeaveCarryForward(@PathVariable Long employeeId) 
            throws ResourceNotFoundException {
        leaveDetailsService.processYearEndLeaveCarryForward(employeeId);
        return ResponseEntity.ok("Year-end leave carry forward processed successfully");
    }

    @GetMapping("/check-availability/{employeeId}")
    public ResponseEntity<Boolean> checkLeaveAvailability(
            @PathVariable Long employeeId,
            @RequestParam String leaveType,
            @RequestParam int days) throws ResourceNotFoundException {
        return ResponseEntity.ok(leaveDetailsService.checkLeaveAvailability(employeeId, leaveType, days));
    }

    @PostMapping("/deduct/{employeeId}")
    public ResponseEntity<String> deductLeaves(
            @PathVariable Long employeeId,
            @RequestParam String leaveType,
            @RequestParam int days) throws ResourceNotFoundException {
        leaveDetailsService.deductLeaves(employeeId, leaveType, days);
        return ResponseEntity.ok("Leaves deducted successfully");
    }
    
    @GetMapping("/test")
    public String test() {
        return "LeaveDetails Controller is working!";
    }
}