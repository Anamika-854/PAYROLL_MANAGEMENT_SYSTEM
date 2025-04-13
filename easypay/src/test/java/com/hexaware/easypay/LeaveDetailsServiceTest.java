package com.hexaware.easypay;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.hexaware.easypay.customexceptions.ResourceNotFoundException;
import com.hexaware.easypay.dto.LeaveDetailsDTO;
import com.hexaware.easypay.model.Employee;
import com.hexaware.easypay.model.LeaveDetails;
import com.hexaware.easypay.repository.EmployeeRepository;
import com.hexaware.easypay.repository.LeaveDetailsRepository;
import com.hexaware.easypay.service.LeaveDetailsServiceImpl;

@ExtendWith(MockitoExtension.class)
public class LeaveDetailsServiceTest {

    @Mock
    private LeaveDetailsRepository leaveDetailsRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private LeaveDetailsServiceImpl leaveDetailsService;

    private LeaveDetails leaveDetails;
    private LeaveDetailsDTO leaveDetailsDTO;
    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setId(1L);

        leaveDetails = new LeaveDetails();
        leaveDetails.setId(1L);
        leaveDetails.setEmployee(employee);

        leaveDetailsDTO = new LeaveDetailsDTO();
        leaveDetailsDTO.setEmployeeId(1L);
    }

    @Test
    void createLeaveDetails_Success() throws ResourceNotFoundException {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(leaveDetailsRepository.save(any(LeaveDetails.class))).thenReturn(leaveDetails);
        when(modelMapper.map(any(), any())).thenReturn(leaveDetailsDTO);

        LeaveDetailsDTO result = leaveDetailsService.createLeaveDetails(leaveDetailsDTO);
        
        assertNotNull(result);
    }

    @Test
    void updateLeaveDetails_Success() throws ResourceNotFoundException {
        when(leaveDetailsRepository.findById(1L)).thenReturn(Optional.of(leaveDetails));
        when(leaveDetailsRepository.save(any(LeaveDetails.class))).thenReturn(leaveDetails);
        when(modelMapper.map(any(), any())).thenReturn(leaveDetailsDTO);

        LeaveDetailsDTO result = leaveDetailsService.updateLeaveDetails(1L, leaveDetailsDTO);
        
        assertNotNull(result);
    }

    @Test
    void getLeaveDetailsByEmployeeId_Success() throws ResourceNotFoundException {
        when(leaveDetailsRepository.findByEmployeeId(1L)).thenReturn(Optional.of(leaveDetails));
        when(modelMapper.map(any(), any())).thenReturn(leaveDetailsDTO);

        LeaveDetailsDTO result = leaveDetailsService.getLeaveDetailsByEmployeeId(1L);
        
        assertNotNull(result);
    }

    @Test
    void checkLeaveAvailability_Success() throws ResourceNotFoundException {
        when(leaveDetailsRepository.findByEmployeeId(1L)).thenReturn(Optional.of(leaveDetails));

        boolean result = leaveDetailsService.checkLeaveAvailability(1L, "CASUAL", 1);
        
        assertTrue(result);
    }

    @Test
    void deductLeaves_Success() throws ResourceNotFoundException {
        when(leaveDetailsRepository.findByEmployeeId(1L)).thenReturn(Optional.of(leaveDetails));
        when(leaveDetailsRepository.save(any(LeaveDetails.class))).thenReturn(leaveDetails);

        assertDoesNotThrow(() -> leaveDetailsService.deductLeaves(1L, "CASUAL", 1));
    }
}