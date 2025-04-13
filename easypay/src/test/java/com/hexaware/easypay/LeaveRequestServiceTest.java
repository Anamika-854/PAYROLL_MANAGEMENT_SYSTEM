package com.hexaware.easypay;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.hexaware.easypay.customexceptions.ResourceNotFoundException;
import com.hexaware.easypay.dto.LeaveRequestDTO;
import com.hexaware.easypay.model.Employee;
import com.hexaware.easypay.model.LeaveRequest;
import com.hexaware.easypay.repository.EmployeeRepository;
import com.hexaware.easypay.repository.LeaveRequestRepository;
import com.hexaware.easypay.service.LeaveRequestServiceImpl;

@ExtendWith(MockitoExtension.class)
public class LeaveRequestServiceTest {

    @Mock
    private LeaveRequestRepository leaveRequestRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private LeaveRequestServiceImpl leaveRequestService;

    private LeaveRequest leaveRequest;
    private LeaveRequestDTO leaveRequestDTO;
    private Employee employee;

    @BeforeEach
    void setUp() {
        // Setup Employee
        employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setEmail("john@hexaware.com");

        // Setup LeaveRequest
        leaveRequest = new LeaveRequest();
        leaveRequest.setId(1L);
        leaveRequest.setEmployee(employee);
        leaveRequest.setStartDate(LocalDate.now());
        leaveRequest.setEndDate(LocalDate.now().plusDays(2));
        leaveRequest.setLeaveType("CASUAL");
        leaveRequest.setStatus("PENDING");  // Important to set initial status
        leaveRequest.setReason("Test Leave");

        // Setup LeaveRequestDTO
        leaveRequestDTO = new LeaveRequestDTO();
        leaveRequestDTO.setId(1L);
        leaveRequestDTO.setEmployeeId(1L);
        leaveRequestDTO.setStartDate(LocalDate.now());
        leaveRequestDTO.setEndDate(LocalDate.now().plusDays(2));
        leaveRequestDTO.setLeaveType("CASUAL");
        leaveRequestDTO.setStatus("PENDING");
        leaveRequestDTO.setReason("Test Leave");
    }

    @Test
    void createLeaveRequest_Success() throws ResourceNotFoundException {
        // Given
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(modelMapper.map(any(LeaveRequestDTO.class), eq(LeaveRequest.class))).thenReturn(leaveRequest);
        when(leaveRequestRepository.save(any(LeaveRequest.class))).thenReturn(leaveRequest);
        when(modelMapper.map(any(LeaveRequest.class), eq(LeaveRequestDTO.class))).thenReturn(leaveRequestDTO);

        // When
        LeaveRequestDTO result = leaveRequestService.createLeaveRequest(leaveRequestDTO);

        // Then
        assertNotNull(result);
        assertEquals(leaveRequestDTO.getLeaveType(), result.getLeaveType());
        assertEquals("PENDING", result.getStatus());
        
        // Verify
        verify(employeeRepository).findById(1L);
        verify(leaveRequestRepository).save(any(LeaveRequest.class));
    }

    @Test
    void getLeaveRequestById_Success() throws ResourceNotFoundException {
        when(leaveRequestRepository.findById(1L)).thenReturn(Optional.of(leaveRequest));
        when(modelMapper.map(any(), any())).thenReturn(leaveRequestDTO);

        LeaveRequestDTO result = leaveRequestService.getLeaveRequestById(1L);
        
        assertNotNull(result);
    }

    @Test
    void getLeaveRequestsByEmployee_Success() {
        when(leaveRequestRepository.findByEmployeeId(1L)).thenReturn(Arrays.asList(leaveRequest));
        when(modelMapper.map(any(), any())).thenReturn(leaveRequestDTO);

        List<LeaveRequestDTO> result = leaveRequestService.getLeaveRequestsByEmployee(1L);
        
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void getLeaveRequestsByManager_Success() {
        when(leaveRequestRepository.findByManagerId(1L)).thenReturn(Arrays.asList(leaveRequest));
        when(modelMapper.map(any(), any())).thenReturn(leaveRequestDTO);

        List<LeaveRequestDTO> result = leaveRequestService.getLeaveRequestsByManager(1L);
        
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void getLeaveRequestsByStatus_Success() {
        when(leaveRequestRepository.findByStatus("PENDING")).thenReturn(Arrays.asList(leaveRequest));
        when(modelMapper.map(any(), any())).thenReturn(leaveRequestDTO);

        List<LeaveRequestDTO> result = leaveRequestService.getLeaveRequestsByStatus("PENDING");
        
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void approveLeaveRequest_Success() throws ResourceNotFoundException {
        // Given
        LeaveRequest pendingRequest = leaveRequest;
        pendingRequest.setStatus("PENDING");
        
        Employee approver = new Employee();
        approver.setId(2L);

        when(leaveRequestRepository.findById(1L)).thenReturn(Optional.of(pendingRequest));
        when(employeeRepository.findById(2L)).thenReturn(Optional.of(approver));
        when(leaveRequestRepository.save(any(LeaveRequest.class))).thenAnswer(invocation -> {
            LeaveRequest savedRequest = invocation.getArgument(0);
            savedRequest.setStatus("APPROVED");
            savedRequest.setApprovedBy(approver);
            return savedRequest;
        });

        // When & Then
        assertDoesNotThrow(() -> {
            leaveRequestService.approveLeaveRequest(1L, 2L);
        });

        // Verify
        verify(leaveRequestRepository).findById(1L);
        verify(employeeRepository).findById(2L);
        verify(leaveRequestRepository).save(any(LeaveRequest.class));
    }
}