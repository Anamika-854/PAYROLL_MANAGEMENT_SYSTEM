package com.hexaware.easypay.service;

import com.hexaware.easypay.dto.SalaryDetailsDTO;
import com.hexaware.easypay.customexceptions.ResourceNotFoundException;
import java.math.BigDecimal;

public interface ISalaryDetailsService {
    SalaryDetailsDTO createSalaryDetails(SalaryDetailsDTO salaryDetailsDTO) throws ResourceNotFoundException;
    SalaryDetailsDTO updateSalaryDetails(Long id, SalaryDetailsDTO salaryDetailsDTO) throws ResourceNotFoundException;
    SalaryDetailsDTO getSalaryDetailsByEmployeeId(Long employeeId) throws ResourceNotFoundException;
    void calculateSalary(Long employeeId) throws ResourceNotFoundException;
    BigDecimal calculateTotalDeductions(SalaryDetailsDTO salaryDetailsDTO);
    BigDecimal calculateNetSalary(SalaryDetailsDTO salaryDetailsDTO);
}