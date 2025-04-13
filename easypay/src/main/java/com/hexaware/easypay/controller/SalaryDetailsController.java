package com.hexaware.easypay.controller;

import java.math.BigDecimal;

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
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.easypay.customexceptions.ResourceNotFoundException;
import com.hexaware.easypay.dto.SalaryDetailsDTO;
import com.hexaware.easypay.service.ISalaryDetailsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/salary-details")
@PreAuthorize("hasRole('ADMIN')")
public class SalaryDetailsController {

    @Autowired
    private ISalaryDetailsService salaryDetailsService;

    @PostMapping
    public ResponseEntity<SalaryDetailsDTO> createSalaryDetails(@Valid @RequestBody SalaryDetailsDTO salaryDetailsDTO) 
            throws ResourceNotFoundException {
        return new ResponseEntity<>(salaryDetailsService.createSalaryDetails(salaryDetailsDTO), 
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalaryDetailsDTO> updateSalaryDetails(
            @PathVariable Long id,
            @Valid @RequestBody SalaryDetailsDTO salaryDetailsDTO) throws ResourceNotFoundException {
        return ResponseEntity.ok(salaryDetailsService.updateSalaryDetails(id, salaryDetailsDTO));
    }

    @PreAuthorize("#employeeId == authentication.principal.id or hasRole('ADMIN')")
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<SalaryDetailsDTO> getSalaryDetailsByEmployeeId(@PathVariable Long employeeId) 
            throws ResourceNotFoundException {
        return ResponseEntity.ok(salaryDetailsService.getSalaryDetailsByEmployeeId(employeeId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/calculate/{employeeId}")
    public ResponseEntity<String> calculateSalary(@PathVariable Long employeeId) 
            throws ResourceNotFoundException {
        salaryDetailsService.calculateSalary(employeeId);
        return ResponseEntity.ok("Salary calculated successfully");
    }

    @GetMapping("/deductions/{employeeId}")
    public ResponseEntity<BigDecimal> getTotalDeductions(@PathVariable Long employeeId) 
            throws ResourceNotFoundException {
        SalaryDetailsDTO dto = salaryDetailsService.getSalaryDetailsByEmployeeId(employeeId);
        return ResponseEntity.ok(salaryDetailsService.calculateTotalDeductions(dto));
    }

    @GetMapping("/net-salary/{employeeId}")
    public ResponseEntity<BigDecimal> getNetSalary(@PathVariable Long employeeId) 
            throws ResourceNotFoundException {
        SalaryDetailsDTO dto = salaryDetailsService.getSalaryDetailsByEmployeeId(employeeId);
        return ResponseEntity.ok(salaryDetailsService.calculateNetSalary(dto));
    }
    
    @GetMapping("/test")
    public String test() {
        return "SalaryDetails Controller is working!";
    }
}