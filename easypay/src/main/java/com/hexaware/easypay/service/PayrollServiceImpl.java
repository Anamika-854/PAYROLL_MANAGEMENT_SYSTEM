package com.hexaware.easypay.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.easypay.customexceptions.ResourceNotFoundException;
import com.hexaware.easypay.dto.PayrollDTO;
import com.hexaware.easypay.model.Employee;
import com.hexaware.easypay.model.Payroll;
import com.hexaware.easypay.model.PayrollStatus;
import com.hexaware.easypay.repository.EmployeeRepository;
import com.hexaware.easypay.repository.PayrollRepository;
import com.itextpdf.text.DocumentException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class PayrollServiceImpl implements IPayrollService {
	@PersistenceContext
    private EntityManager entityManager;
	
	@Autowired
	private PdfService pdfService;

	@Autowired
    private PayrollRepository payrollRepository;
	@Autowired
    private EmployeeRepository employeeRepository;
	@Autowired
    private ModelMapper modelMapper;

    @Autowired
    public PayrollServiceImpl(PayrollRepository payrollRepository, 
                            EmployeeRepository employeeRepository,
                            ModelMapper modelMapper) {
        this.payrollRepository = payrollRepository;
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PayrollDTO createPayroll(PayrollDTO payrollDTO) throws ResourceNotFoundException {
        try {
            Employee employee = employeeRepository.findById(payrollDTO.getEmployeeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", payrollDTO.getEmployeeId()));

            // Use modelMapper to convert DTO to Entity
            Payroll payroll = new Payroll();
            payroll.setEmployee(employee);
            payroll.setPayPeriodStart(payrollDTO.getPayPeriodStart());
            payroll.setPayPeriodEnd(payrollDTO.getPayPeriodEnd());
            payroll.setBasicSalary(payrollDTO.getBasicSalary() != null ? payrollDTO.getBasicSalary() : BigDecimal.ZERO);
            payroll.setAllowances(payrollDTO.getAllowances() != null ? payrollDTO.getAllowances() : BigDecimal.ZERO);
            payroll.setDeductions(payrollDTO.getDeductions() != null ? payrollDTO.getDeductions() : BigDecimal.ZERO);
            payroll.setStatus(PayrollStatus.DRAFT);

            // Save and convert back to DTO
            Payroll savedPayroll = payrollRepository.save(payroll);
            return modelMapper.map(savedPayroll, PayrollDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Error creating payroll: " + e.getMessage());
        }
    }

    @Override
    public PayrollDTO getPayrollById(Long id) throws ResourceNotFoundException {
        Payroll payroll = payrollRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payroll", "id", id));
        return modelMapper.map(payroll, PayrollDTO.class);
    }

    @Override
    public List<PayrollDTO> getPayrollsByEmployee(Long employeeId) {
        return payrollRepository.findByEmployeeId(employeeId).stream()
                .map(payroll -> modelMapper.map(payroll, PayrollDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PayrollDTO> getPayrollsByPeriod(LocalDate startDate, LocalDate endDate) {
        return payrollRepository.findByPeriod(startDate, endDate).stream()
                .map(payroll -> modelMapper.map(payroll, PayrollDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PayrollDTO> getPayrollsByStatus(PayrollStatus status) {
        return payrollRepository.findByStatus(status).stream()
                .map(payroll -> modelMapper.map(payroll, PayrollDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> processPayroll(Long id) throws ResourceNotFoundException {
        // Get payroll
        Payroll payroll = payrollRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payroll", "id", id));

        // Validate current status
        if (payroll.getStatus() != PayrollStatus.DRAFT) {
            throw new IllegalStateException("Payroll can only be processed when in DRAFT status");
        }

        try {
            // Calculate values
            BigDecimal basicSalary = payroll.getBasicSalary() != null ? 
                payroll.getBasicSalary() : BigDecimal.ZERO;
            BigDecimal allowances = payroll.getAllowances() != null ? 
                payroll.getAllowances() : BigDecimal.ZERO;
            BigDecimal deductions = payroll.getDeductions() != null ? 
                payroll.getDeductions() : BigDecimal.ZERO;
            BigDecimal netSalary = basicSalary.add(allowances).subtract(deductions);

            // Update using native query
            entityManager.createNativeQuery("""
                UPDATE payrolls SET 
                    status = :status,
                    net_salary = :netSalary,
                    allowances = :allowances,
                    deductions = :deductions,
                    date_updated = NOW()
                WHERE id = :id
                """)
                .setParameter("status", PayrollStatus.PROCESSED.name())
                .setParameter("netSalary", netSalary)
                .setParameter("allowances", allowances)
                .setParameter("deductions", deductions)
                .setParameter("id", id)
                .executeUpdate();

            // Prepare response
            Map<String, Object> result = new HashMap<>();
            result.put("payrollId", id);
            result.put("employeeId", payroll.getEmployee().getId());
            result.put("status", PayrollStatus.PROCESSED);
            result.put("processedAt", LocalDateTime.now());
            result.put("amounts", Map.of(
                "basicSalary", basicSalary,
                "allowances", allowances,
                "deductions", deductions,
                "netSalary", netSalary
            ));
            result.put("payPeriod", Map.of(
                "startDate", payroll.getPayPeriodStart(),
                "endDate", payroll.getPayPeriodEnd()
            ));

            return result;

        } catch (Exception e) {
            throw new RuntimeException("Error processing payroll: " + e.getMessage());
        }
    }

    @Override
    public Map<String, Object> approvePayroll(Long id) throws ResourceNotFoundException {
        // Get payroll
        Payroll payroll = payrollRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payroll", "id", id));

        // Validate status
        if (payroll.getStatus() != PayrollStatus.PROCESSED) {
            throw new IllegalStateException("Payroll can only be approved when in PROCESSED status");
        }

        try {
            // Update using native query
            int updated = entityManager.createNativeQuery("""
                UPDATE payrolls SET 
                    status = :status,
                    date_updated = NOW()
                WHERE id = :id AND status = :currentStatus
                """)
                .setParameter("status", PayrollStatus.APPROVED.name())
                .setParameter("id", id)
                .setParameter("currentStatus", PayrollStatus.PROCESSED.name())
                .executeUpdate();

            if (updated == 0) {
                throw new IllegalStateException("Payroll status has been changed. Please refresh and try again.");
            }

            // Prepare response
            Map<String, Object> result = new HashMap<>();
            result.put("payrollId", id);
            result.put("employeeId", payroll.getEmployee().getId());
            result.put("status", PayrollStatus.APPROVED);
            result.put("approvedAt", LocalDateTime.now());
            result.put("amounts", Map.of(
                "basicSalary", payroll.getBasicSalary(),
                "allowances", payroll.getAllowances(),
                "deductions", payroll.getDeductions(),
                "netSalary", payroll.getNetSalary()
            ));

            return result;

        } catch (Exception e) {
            throw new RuntimeException("Error approving payroll: " + e.getMessage());
        }
    }

    // Helper method to validate payroll before approval
    private void validateApproval(Payroll payroll) {
        // Check if net salary is calculated
        if (payroll.getNetSalary() == null || payroll.getNetSalary().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalStateException("Net salary must be calculated and greater than zero");
        }

        // Check if pay period is valid
        if (payroll.getPayPeriodEnd().isBefore(payroll.getPayPeriodStart())) {
            throw new IllegalStateException("Pay period end date cannot be before start date");
        }

        // Add any other validation rules your business requires
        // For example:
        // - Check if the pay period is within allowed ranges
        // - Validate deductions don't exceed certain percentage of basic salary
        // - Ensure all required fields are populated
    }
    
    
//    private void calculatePayroll(Payroll payroll) {
//        try {
//            // Basic calculation logic
//            BigDecimal totalAllowances = payroll.getAllowances() != null ? 
//                payroll.getAllowances() : BigDecimal.ZERO;
//            BigDecimal totalDeductions = payroll.getDeductions() != null ? 
//                payroll.getDeductions() : BigDecimal.ZERO;
//            
//            BigDecimal netSalary = payroll.getBasicSalary()
//                    .add(totalAllowances)
//                    .subtract(totalDeductions);
//            
//            payroll.setNetSalary(netSalary);
//        } catch (Exception e) {
//            throw new RuntimeException("Error calculating payroll: " + e.getMessage());
//        }
//    }

    @Override
    public PayrollDTO updatePayroll(Long id, PayrollDTO payrollDTO) throws ResourceNotFoundException {
        Payroll payroll = payrollRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payroll", "id", id));
        
        if (payroll.getStatus().equals("PAID")) {
            throw new IllegalStateException("Cannot update a paid payroll");
        }
        
        payroll.setBasicSalary(payrollDTO.getBasicSalary());
        payroll.setAllowances(payrollDTO.getAllowances());
        payroll.setDeductions(payrollDTO.getDeductions());
        
        // Recalculate net salary
        BigDecimal netSalary = payroll.getBasicSalary()
                .add(payroll.getAllowances())
                .subtract(payroll.getDeductions());
        payroll.setNetSalary(netSalary);

        Payroll updatedPayroll = payrollRepository.save(payroll);
        return modelMapper.map(updatedPayroll, PayrollDTO.class);
    }
    
    @Override
    public byte[] downloadPayrollPdf(Long id) throws ResourceNotFoundException, DocumentException {
        Payroll payroll = payrollRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payroll", "id", id));
        
        Employee employee = payroll.getEmployee();
        
        return pdfService.generatePayrollPdf(payroll, employee);
    }
}