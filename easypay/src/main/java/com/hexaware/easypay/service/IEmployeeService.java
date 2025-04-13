package com.hexaware.easypay.service;

import java.util.List;
import java.util.Optional;

import com.hexaware.easypay.customexceptions.ResourceNotFoundException;
import com.hexaware.easypay.dto.EmployeeDTO;
import com.hexaware.easypay.model.Employee;

public interface IEmployeeService {
    EmployeeDTO addEmployee(EmployeeDTO employeeDTO);
    EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) throws ResourceNotFoundException;
    EmployeeDTO getEmployeeById(Long id) throws ResourceNotFoundException;
    List<EmployeeDTO> getAllEmployees();
    List<EmployeeDTO> getActiveEmployees();
    void deleteEmployee(Long id) throws ResourceNotFoundException;
    List<EmployeeDTO> getEmployeesByManager(Long managerId);
    
 // New method for employee verification
    boolean verifyEmployeeDetails(String employeeCode, String email);
    
    Optional<Employee> findByEmail(String email);
}