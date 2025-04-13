package com.hexaware.easypay.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hexaware.easypay.model.SalaryDetails;

public interface SalaryDetailsRepository extends JpaRepository<SalaryDetails, Long> {
    Optional<SalaryDetails> findByEmployeeId(Long employeeId);
    
    @Query("SELECT sd FROM SalaryDetails sd WHERE sd.employee.id = :employeeId AND " +
           "sd.netSalary BETWEEN :minSalary AND :maxSalary")
    List<SalaryDetails> findByEmployeeIdAndSalaryRange(
        Long employeeId, BigDecimal minSalary, BigDecimal maxSalary);
}

