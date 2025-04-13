package com.hexaware.easypay.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hexaware.easypay.model.LeaveDetails;

public interface LeaveDetailsRepository extends JpaRepository<LeaveDetails, Long> {
    Optional<LeaveDetails> findByEmployeeId(Long employeeId);
    
    @Query("SELECT ld FROM LeaveDetails ld WHERE ld.carryForwardLeavesTotal > 0")
    List<LeaveDetails> findAllWithCarryForwardLeaves();
}