package com.hexaware.easypay.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.hexaware.easypay.model.PayrollStatus;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PayrollDTO {
    private Long id;
    
    @NotNull(message = "Employee ID is required")
    private Long employeeId;
    
    @NotNull(message = "Pay period start date is required")
    private LocalDate payPeriodStart;
    
    @NotNull(message = "Pay period end date is required")
    private LocalDate payPeriodEnd;
    
    @Positive(message = "Basic salary must be positive")
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal basicSalary;
    
    private BigDecimal allowances;
    private BigDecimal deductions;
    private BigDecimal netSalary;
    private PayrollStatus status;
	public PayrollDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PayrollDTO(Long id, @NotNull(message = "Employee ID is required") Long employeeId,
			@NotNull(message = "Pay period start date is required") LocalDate payPeriodStart,
			@NotNull(message = "Pay period end date is required") LocalDate payPeriodEnd,
			@Positive(message = "Basic salary must be positive") BigDecimal basicSalary, BigDecimal allowances,
			BigDecimal deductions, BigDecimal netSalary, PayrollStatus status) {
		super();
		this.id = id;
		this.employeeId = employeeId;
		this.payPeriodStart = payPeriodStart;
		this.payPeriodEnd = payPeriodEnd;
		this.basicSalary = basicSalary;
		this.allowances = allowances;
		this.deductions = deductions;
		this.netSalary = netSalary;
		this.status = status;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public LocalDate getPayPeriodStart() {
		return payPeriodStart;
	}
	public void setPayPeriodStart(LocalDate payPeriodStart) {
		this.payPeriodStart = payPeriodStart;
	}
	public LocalDate getPayPeriodEnd() {
		return payPeriodEnd;
	}
	public void setPayPeriodEnd(LocalDate payPeriodEnd) {
		this.payPeriodEnd = payPeriodEnd;
	}
	public BigDecimal getBasicSalary() {
		return basicSalary;
	}
	public void setBasicSalary(BigDecimal basicSalary) {
		this.basicSalary = basicSalary;
	}
	public BigDecimal getAllowances() {
		return allowances;
	}
	public void setAllowances(BigDecimal allowances) {
		this.allowances = allowances;
	}
	public BigDecimal getDeductions() {
		return deductions;
	}
	public void setDeductions(BigDecimal deductions) {
		this.deductions = deductions;
	}
	public BigDecimal getNetSalary() {
		return netSalary;
	}
	public void setNetSalary(BigDecimal netSalary) {
		this.netSalary = netSalary;
	}
	public PayrollStatus getStatus() {
		return status;
	}
	public void setStatus(PayrollStatus payrollStatus) {
		this.status = payrollStatus;
	}
	@Override
	public String toString() {
		return "PayrollDTO [id=" + id + ", employeeId=" + employeeId + ", payPeriodStart=" + payPeriodStart
				+ ", payPeriodEnd=" + payPeriodEnd + ", basicSalary=" + basicSalary + ", allowances=" + allowances
				+ ", deductions=" + deductions + ", netSalary=" + netSalary + ", status=" + status + "]";
	}

    // Constructors, Getters, and Setters, toString
    
}
