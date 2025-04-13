package com.hexaware.easypay;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.hexaware.easypay.customexceptions.ResourceNotFoundException;
import com.hexaware.easypay.dto.PayrollDTO;
import com.hexaware.easypay.model.Employee;
import com.hexaware.easypay.model.Payroll;
import com.hexaware.easypay.model.PayrollStatus;
import com.hexaware.easypay.repository.EmployeeRepository;
import com.hexaware.easypay.repository.PayrollRepository;
import com.hexaware.easypay.service.PayrollServiceImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@ExtendWith(MockitoExtension.class)
public class PayrollServiceTest {

    @Mock
    private PayrollRepository payrollRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ModelMapper modelMapper;
    
    @Mock
    private EntityManager entityManager;

    @Mock
    private Query query;

    @InjectMocks
    private PayrollServiceImpl payrollService;

    private Payroll payroll;
    private PayrollDTO payrollDTO;
    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setId(1L);
        employee.setBasicSalary(new BigDecimal("50000"));

        payroll = new Payroll();
        payroll.setId(1L);
        payroll.setEmployee(employee);
        payroll.setBasicSalary(new BigDecimal("50000"));
        payroll.setAllowances(new BigDecimal("10000"));
        payroll.setDeductions(new BigDecimal("5000"));
        payroll.setPayPeriodStart(LocalDate.now());
        payroll.setPayPeriodEnd(LocalDate.now().plusMonths(1));
        // Status will be set in individual test cases

        payrollDTO = new PayrollDTO();
        payrollDTO.setId(1L);
        payrollDTO.setEmployeeId(1L);
        payrollDTO.setBasicSalary(new BigDecimal("50000"));
        payrollDTO.setAllowances(new BigDecimal("10000"));
        payrollDTO.setDeductions(new BigDecimal("5000"));
        payrollDTO.setPayPeriodStart(LocalDate.now());
        payrollDTO.setPayPeriodEnd(LocalDate.now().plusMonths(1));
    }

    @Test
    void createPayroll_Success() throws ResourceNotFoundException {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(payrollRepository.save(any(Payroll.class))).thenReturn(payroll);
        when(modelMapper.map(any(), any())).thenReturn(payrollDTO);

        PayrollDTO result = payrollService.createPayroll(payrollDTO);
        
        assertNotNull(result);
        assertEquals(payrollDTO.getEmployeeId(), result.getEmployeeId());
    }

    @Test
    void updatePayroll_Success() throws ResourceNotFoundException {
        // Given
        payroll.setStatus(PayrollStatus.DRAFT); // Set status to DRAFT for update
        when(payrollRepository.findById(1L)).thenReturn(Optional.of(payroll));
        when(payrollRepository.save(any(Payroll.class))).thenReturn(payroll);
        when(modelMapper.map(any(Payroll.class), eq(PayrollDTO.class))).thenReturn(payrollDTO);

        // When
        PayrollDTO result = payrollService.updatePayroll(1L, payrollDTO);

        // Then
        assertNotNull(result);
        assertEquals(payrollDTO.getBasicSalary(), result.getBasicSalary());
        verify(payrollRepository).findById(1L);
        verify(payrollRepository).save(any(Payroll.class));
    }

    @Test
    void getPayrollById_Success() throws ResourceNotFoundException {
        when(payrollRepository.findById(1L)).thenReturn(Optional.of(payroll));
        when(modelMapper.map(any(), any())).thenReturn(payrollDTO);

        PayrollDTO result = payrollService.getPayrollById(1L);
        
        assertNotNull(result);
        assertEquals(payrollDTO.getId(), result.getId());
    }

    @Test
    void getPayrollsByEmployee_Success() {
        when(payrollRepository.findByEmployeeId(1L)).thenReturn(Arrays.asList(payroll));
        when(modelMapper.map(any(), any())).thenReturn(payrollDTO);

        List<PayrollDTO> result = payrollService.getPayrollsByEmployee(1L);
        
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void getPayrollsByPeriod_Success() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusMonths(1);
        
        when(payrollRepository.findByPeriod(startDate, endDate))
            .thenReturn(Arrays.asList(payroll));
        when(modelMapper.map(any(), any())).thenReturn(payrollDTO);

        List<PayrollDTO> result = payrollService.getPayrollsByPeriod(startDate, endDate);
        
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void getPayrollsByStatus_Success() {
        when(payrollRepository.findByStatus(PayrollStatus.DRAFT)).thenReturn(Arrays.asList(payroll));
        when(modelMapper.map(any(), any())).thenReturn(payrollDTO);

        List<PayrollDTO> result = payrollService.getPayrollsByStatus(PayrollStatus.DRAFT);
        
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void processPayroll_Success() throws ResourceNotFoundException {
        // Given
        payroll.setStatus(PayrollStatus.DRAFT);
        when(payrollRepository.findById(1L)).thenReturn(Optional.of(payroll));
        
        // Mock EntityManager behavior
        when(entityManager.createNativeQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.executeUpdate()).thenReturn(1);

        // When & Then
        assertDoesNotThrow(() -> {
            Map<String, Object> result = payrollService.processPayroll(1L);
            assertNotNull(result);
            assertEquals(PayrollStatus.PROCESSED.name(), result.get("status"));
        });

        verify(payrollRepository).findById(1L);
        verify(entityManager).createNativeQuery(anyString());
    }
    @Test
    void approvePayroll_Success() throws ResourceNotFoundException {
        // Given
        payroll.setStatus(PayrollStatus.PROCESSED);
        when(payrollRepository.findById(1L)).thenReturn(Optional.of(payroll));
        
        // Mock EntityManager behavior
        when(entityManager.createNativeQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.executeUpdate()).thenReturn(1);

        // When & Then
        assertDoesNotThrow(() -> {
            Map<String, Object> result = payrollService.approvePayroll(1L);
            assertNotNull(result);
            assertEquals(PayrollStatus.APPROVED.name(), result.get("status"));
        });

        verify(payrollRepository).findById(1L);
        verify(entityManager).createNativeQuery(anyString());
    }
}