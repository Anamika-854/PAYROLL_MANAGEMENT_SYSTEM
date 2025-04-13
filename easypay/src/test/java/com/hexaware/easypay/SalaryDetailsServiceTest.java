package com.hexaware.easypay;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.hexaware.easypay.customexceptions.ResourceNotFoundException;
import com.hexaware.easypay.dto.SalaryDetailsDTO;
import com.hexaware.easypay.model.Employee;
import com.hexaware.easypay.model.SalaryDetails;
import com.hexaware.easypay.repository.EmployeeRepository;
import com.hexaware.easypay.repository.SalaryDetailsRepository;
import com.hexaware.easypay.service.SalaryDetailsServiceImpl;

@ExtendWith(MockitoExtension.class)
public class SalaryDetailsServiceTest {

    @Mock
    private SalaryDetailsRepository salaryDetailsRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private SalaryDetailsServiceImpl salaryDetailsService;

    private SalaryDetails salaryDetails;
    private SalaryDetailsDTO salaryDetailsDTO;
    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setId(1L);

        salaryDetails = new SalaryDetails();
        salaryDetails.setId(1L);
        salaryDetails.setEmployee(employee);
        salaryDetails.setBasicSalary(new BigDecimal("50000"));
        salaryDetails.setHouseRentAllowance(new BigDecimal("20000"));
        salaryDetails.setDearnessAllowance(new BigDecimal("10000"));
        salaryDetails.setProvidentFund(new BigDecimal("6000"));
        salaryDetails.setProfessionalTax(new BigDecimal("200"));
        salaryDetails.setIncomeTax(new BigDecimal("5000"));

        salaryDetailsDTO = new SalaryDetailsDTO();
        salaryDetailsDTO.setEmployeeId(1L);
        salaryDetailsDTO.setBasicSalary(new BigDecimal("50000"));
        salaryDetailsDTO.setHouseRentAllowance(new BigDecimal("20000"));
        salaryDetailsDTO.setDearnessAllowance(new BigDecimal("10000"));
        salaryDetailsDTO.setProvidentFund(new BigDecimal("6000"));
        salaryDetailsDTO.setProfessionalTax(new BigDecimal("200"));
        salaryDetailsDTO.setIncomeTax(new BigDecimal("5000"));
    }

    @Test
    void createSalaryDetails_Success() throws ResourceNotFoundException {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(salaryDetailsRepository.findByEmployeeId(1L)).thenReturn(Optional.empty());
        when(salaryDetailsRepository.save(any(SalaryDetails.class))).thenReturn(salaryDetails);
        when(modelMapper.map(any(), any())).thenReturn(salaryDetailsDTO);

        SalaryDetailsDTO result = salaryDetailsService.createSalaryDetails(salaryDetailsDTO);
        
        assertNotNull(result);
        assertEquals(salaryDetailsDTO.getBasicSalary(), result.getBasicSalary());
    }

    @Test
    void updateSalaryDetails_Success() throws ResourceNotFoundException {
        when(salaryDetailsRepository.findById(1L)).thenReturn(Optional.of(salaryDetails));
        when(salaryDetailsRepository.save(any(SalaryDetails.class))).thenReturn(salaryDetails);
        when(modelMapper.map(any(), any())).thenReturn(salaryDetailsDTO);

        SalaryDetailsDTO result = salaryDetailsService.updateSalaryDetails(1L, salaryDetailsDTO);
        
        assertNotNull(result);
        assertEquals(salaryDetailsDTO.getBasicSalary(), result.getBasicSalary());
    }

    @Test
    void getSalaryDetailsByEmployeeId_Success() throws ResourceNotFoundException {
        when(salaryDetailsRepository.findByEmployeeId(1L)).thenReturn(Optional.of(salaryDetails));
        when(modelMapper.map(any(), any())).thenReturn(salaryDetailsDTO);

        SalaryDetailsDTO result = salaryDetailsService.getSalaryDetailsByEmployeeId(1L);
        
        assertNotNull(result);
        assertEquals(salaryDetailsDTO.getEmployeeId(), result.getEmployeeId());
    }

    @Test
    void calculateSalary_Success() throws ResourceNotFoundException {
        when(salaryDetailsRepository.findByEmployeeId(1L)).thenReturn(Optional.of(salaryDetails));
        when(salaryDetailsRepository.save(any(SalaryDetails.class))).thenReturn(salaryDetails);

        assertDoesNotThrow(() -> salaryDetailsService.calculateSalary(1L));
    }

    @Test
    void calculateTotalDeductions_Success() {
        BigDecimal expectedDeductions = salaryDetailsDTO.getProvidentFund()
                .add(salaryDetailsDTO.getProfessionalTax())
                .add(salaryDetailsDTO.getIncomeTax());

        BigDecimal result = salaryDetailsService.calculateTotalDeductions(salaryDetailsDTO);
        
        assertEquals(expectedDeductions, result);
    }

    @Test
    void calculateNetSalary_Success() {
        // Set gross salary in DTO
        salaryDetailsDTO.setGrossSalary(new BigDecimal("80000"));
        BigDecimal totalDeductions = salaryDetailsDTO.getProvidentFund()
                .add(salaryDetailsDTO.getProfessionalTax())
                .add(salaryDetailsDTO.getIncomeTax());
        BigDecimal expectedNetSalary = salaryDetailsDTO.getGrossSalary().subtract(totalDeductions);

        BigDecimal result = salaryDetailsService.calculateNetSalary(salaryDetailsDTO);
        
        assertEquals(expectedNetSalary, result);
    }

//    @Test
//    void getSalaryDetailsByEmployeeId_ThrowsException() {
//        when(salaryDetailsRepository.findByEmployeeId(1L)).thenReturn(Optional.empty());
//
//        assertThrows(ResourceNotFoundException.class, 
//                () -> salaryDetailsService.getSalaryDetailsByEmployeeId(1L));
//    }
//
//    @Test
//    void updateSalaryDetails_ThrowsException() {
//        when(salaryDetailsRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(ResourceNotFoundException.class, 
//                () -> salaryDetailsService.updateSalaryDetails(1L, salaryDetailsDTO));
//    }
//
//    @Test
//    void calculateSalary_ThrowsException() {
//        when(salaryDetailsRepository.findByEmployeeId(1L)).thenReturn(Optional.empty());
//
//        assertThrows(ResourceNotFoundException.class, 
//                () -> salaryDetailsService.calculateSalary(1L));
//    }
}