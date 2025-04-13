package com.hexaware.easypay.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.hexaware.easypay.customexceptions.ResourceNotFoundException;
import com.hexaware.easypay.dto.PayrollDTO;
import com.hexaware.easypay.model.PayrollStatus;
import com.itextpdf.text.DocumentException;

public interface IPayrollService {
    PayrollDTO createPayroll(PayrollDTO payrollDTO) throws ResourceNotFoundException;
    PayrollDTO updatePayroll(Long id, PayrollDTO payrollDTO) throws ResourceNotFoundException;
    PayrollDTO getPayrollById(Long id) throws ResourceNotFoundException;
    List<PayrollDTO> getPayrollsByEmployee(Long employeeId);
    List<PayrollDTO> getPayrollsByPeriod(LocalDate startDate, LocalDate endDate);
    Map<String, Object> processPayroll(Long id) throws ResourceNotFoundException;
    Map<String, Object> approvePayroll(Long id) throws ResourceNotFoundException;
	List<PayrollDTO> getPayrollsByStatus(PayrollStatus status);
	byte[] downloadPayrollPdf(Long id) throws ResourceNotFoundException, DocumentException;
}