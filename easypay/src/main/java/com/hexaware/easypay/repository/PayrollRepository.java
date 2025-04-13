package com.hexaware.easypay.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hexaware.easypay.model.Payroll;
import com.hexaware.easypay.model.PayrollStatus;

public interface PayrollRepository extends JpaRepository<Payroll, Long> {
    List<Payroll> findByEmployeeId(Long employeeId);
    
    @Query("SELECT p FROM Payroll p WHERE p.payPeriodStart >= :startDate AND p.payPeriodEnd <= :endDate")
    List<Payroll> findByPeriod(LocalDate startDate, LocalDate endDate);
    
    List<Payroll> findByStatus(PayrollStatus status);
}