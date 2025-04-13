package com.hexaware.easypay.repository;

import com.hexaware.easypay.model.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByEmployeeId(Long employeeId);
    List<LeaveRequest> findByStatus(String status);
    
    @Query("SELECT l FROM LeaveRequest l WHERE l.employee.manager.id = :managerId")
    List<LeaveRequest> findByManagerId(Long managerId);
}