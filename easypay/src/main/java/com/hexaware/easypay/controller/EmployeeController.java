package com.hexaware.easypay.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.easypay.customexceptions.ResourceNotFoundException;
import com.hexaware.easypay.dto.EmployeeDTO;
import com.hexaware.easypay.model.Employee;
import com.hexaware.easypay.repository.EmployeeRepository;
import com.hexaware.easypay.service.IEmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employees")
@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
public class EmployeeController {

    private final IEmployeeService employeeService;

    @Autowired
    public EmployeeController(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    
    @Autowired
    private EmployeeRepository employeeRepository;
    	    

    @GetMapping("/verify/{employeeCode}/{email}")
    public ResponseEntity<Map<String, Boolean>> verifyEmployee(
            @PathVariable String employeeCode,
            @PathVariable String email) {
        Map<String, Boolean> response = new HashMap<>();
        Optional<Employee> employee = employeeRepository.findByEmployeeCodeAndEmail(employeeCode, email);
        response.put("verified", employee.isPresent() && employee.get().isActive());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/verify-email/{email}")
    public ResponseEntity<Map<String, Boolean>> verifyEmail(@PathVariable String email) {
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", employeeService.findByEmail(email).isPresent());
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')") // Only admin can create employees
    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        return new ResponseEntity<>(employeeService.addEmployee(employeeDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')") // Both admin and manager can view
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    
    @GetMapping("/active")
    public ResponseEntity<List<EmployeeDTO>> getActiveEmployees() {
        return ResponseEntity.ok(employeeService.getActiveEmployees());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeDTO employeeDTO) throws ResourceNotFoundException {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employeeDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) throws ResourceNotFoundException {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee successfully deactivated");
    }

    @GetMapping("/manager/{managerId}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByManager(@PathVariable Long managerId) {
        return ResponseEntity.ok(employeeService.getEmployeesByManager(managerId));
    }
    
    @GetMapping("/test")
    public String test() {
        return "Employee Controller is working!";
    }
}