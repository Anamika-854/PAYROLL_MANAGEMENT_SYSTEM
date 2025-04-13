package com.hexaware.easypay;

import com.hexaware.easypay.dto.EmployeeDTO;
import com.hexaware.easypay.model.Employee;
import com.hexaware.easypay.repository.EmployeeRepository;
import com.hexaware.easypay.service.EmployeeServiceImpl;
import com.hexaware.easypay.customexceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;
    private EmployeeDTO employeeDTO;

    @BeforeEach
    void setUp() {
        // Setup Employee
        employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john.doe@hexaware.com");
        employee.setPhoneNumber("1234567890");
        employee.setDepartment("IT");
        employee.setDesignation("Developer");
        employee.setJoiningDate(LocalDate.now());
        employee.setBasicSalary(new BigDecimal("50000"));
        employee.setActive(true);

        // Setup EmployeeDTO
        employeeDTO = new EmployeeDTO();
        employeeDTO.setId(1L);
        employeeDTO.setFirstName("John");
        employeeDTO.setLastName("Doe");
        employeeDTO.setEmail("john.doe@hexaware.com");
        employeeDTO.setPhoneNumber("1234567890");
        employeeDTO.setDepartment("IT");
        employeeDTO.setDesignation("Developer");
        employeeDTO.setJoiningDate(LocalDate.now());
        employeeDTO.setBasicSalary(new BigDecimal("50000"));
        employeeDTO.setActive(true);
    }

    @Test
    void addEmployee_Success() {
        // Given
        employeeDTO.setEmail("test@hexaware.com");
        employee.setEmail("test@hexaware.com");

        // Mock only the necessary behaviors
        when(employeeRepository.existsByEmail(employeeDTO.getEmail())).thenReturn(false);
        when(modelMapper.map(employeeDTO, Employee.class)).thenReturn(employee);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        when(modelMapper.map(employee, EmployeeDTO.class)).thenReturn(employeeDTO);

        // When
        EmployeeDTO result = employeeService.addEmployee(employeeDTO);

        // Then
        assertNotNull(result);
        assertEquals(employeeDTO.getEmail(), result.getEmail());
        assertEquals(employeeDTO.getFirstName(), result.getFirstName());

        // Verify only the interactions that actually happen
        verify(employeeRepository).existsByEmail(employeeDTO.getEmail());
        verify(employeeRepository).save(any(Employee.class));
        verify(modelMapper).map(employeeDTO, Employee.class);
        verify(modelMapper).map(any(Employee.class), eq(EmployeeDTO.class));
    }

    @Test
    void updateEmployee_Success() throws ResourceNotFoundException {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        when(modelMapper.map(any(Employee.class), eq(EmployeeDTO.class))).thenReturn(employeeDTO);

        EmployeeDTO result = employeeService.updateEmployee(1L, employeeDTO);
        
        assertNotNull(result);
        assertEquals(employeeDTO.getFirstName(), result.getFirstName());
        verify(employeeRepository).findById(1L);
        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    void getEmployeeById_Success() throws ResourceNotFoundException {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(modelMapper.map(any(Employee.class), eq(EmployeeDTO.class))).thenReturn(employeeDTO);

        EmployeeDTO result = employeeService.getEmployeeById(1L);
        
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(employeeRepository).findById(1L);
    }

    @Test
    void getAllEmployees_Success() {
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee));
        when(modelMapper.map(any(Employee.class), eq(EmployeeDTO.class))).thenReturn(employeeDTO);

        List<EmployeeDTO> result = employeeService.getAllEmployees();
        
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(employeeRepository).findAll();
    }

    @Test
    void getActiveEmployees_Success() {
        when(employeeRepository.findByActiveTrue()).thenReturn(Arrays.asList(employee));
        when(modelMapper.map(any(Employee.class), eq(EmployeeDTO.class))).thenReturn(employeeDTO);

        List<EmployeeDTO> result = employeeService.getActiveEmployees();
        
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(employeeRepository).findByActiveTrue();
    }

    @Test
    void getEmployeesByManager_Success() {
        when(employeeRepository.findByManagerId(1L)).thenReturn(Arrays.asList(employee));
        when(modelMapper.map(any(Employee.class), eq(EmployeeDTO.class))).thenReturn(employeeDTO);

        List<EmployeeDTO> result = employeeService.getEmployeesByManager(1L);
        
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(employeeRepository).findByManagerId(1L);
    }

//    @Test
//    void addEmployee_EmailExists_ThrowsException() {
//        // Given
//        employeeDTO.setEmail("test@hexaware.com");
//        when(employeeRepository.existsByEmail(employeeDTO.getEmail())).thenReturn(true);
//
//        // When & Then
//        Exception exception = assertThrows(RuntimeException.class, () -> {
//            employeeService.addEmployee(employeeDTO);
//        });
//
//        // Verify the error message
//        assertTrue(exception.getMessage().contains("Email already exists"));
//        
//        // Verify method calls
//        verify(employeeRepository).existsByEmail(anyString());
//        verify(employeeRepository, never()).save(any());
//    }
//
//    @Test
//    void getEmployeeById_NotFound_ThrowsException() {
//        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(ResourceNotFoundException.class, () -> {
//            employeeService.getEmployeeById(1L);
//        });
//        
//        verify(employeeRepository).findById(1L);
//    }
}