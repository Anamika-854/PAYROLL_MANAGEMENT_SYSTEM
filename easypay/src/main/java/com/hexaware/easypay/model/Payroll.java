package com.hexaware.easypay.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.hexaware.easypay.customvalidator.PayrollValidation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "payrolls")
public class Payroll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
    
    @Column(nullable = false)
    private LocalDate payPeriodStart;
    
    @Column(nullable = false)
    private LocalDate payPeriodEnd;
    
    @Column(nullable = false, precision = 38, scale = 2)
    private BigDecimal basicSalary;
    
    @Column(precision = 38, scale = 2)
    private BigDecimal allowances;
    @Column(precision = 38, scale = 2)
    private BigDecimal deductions;
    @Column(precision = 38, scale = 2)
    private BigDecimal netSalary;
    
    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private PayrollStatus status = PayrollStatus.DRAFT;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "processed_by")
//    private Employee processedBy;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "approved_by")
//    private Employee approvedBy;
    
    @CreationTimestamp
    private LocalDateTime dateCreated;
    
    @UpdateTimestamp
    private LocalDateTime dateUpdated;

	public Payroll() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Payroll(Long id, Employee employee, LocalDate payPeriodStart, LocalDate payPeriodEnd, BigDecimal basicSalary,
			BigDecimal allowances, BigDecimal deductions, BigDecimal netSalary, PayrollStatus status,
			Employee processedBy, Employee approvedBy, LocalDateTime dateCreated, LocalDateTime dateUpdated) {
		super();
		this.id = id;
		this.employee = employee;
		this.payPeriodStart = payPeriodStart;
		this.payPeriodEnd = payPeriodEnd;
		this.basicSalary = basicSalary;
		this.allowances = allowances;
		this.deductions = deductions;
		this.netSalary = netSalary;
		this.status = status;
//		this.processedBy = processedBy;
//		this.approvedBy = approvedBy;
		this.dateCreated = dateCreated;
		this.dateUpdated = dateUpdated;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
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

	public void setStatus(PayrollStatus status) {
		this.status = status;
	}

//	public Employee getProcessedBy() {
//		return processedBy;
//	}
//
//	public void setProcessedBy(Employee processedBy) {
//		this.processedBy = processedBy;
//	}
//
//	public Employee getApprovedBy() {
//		return approvedBy;
//	}
//
//	public void setApprovedBy(Employee approvedBy) {
//		this.approvedBy = approvedBy;
//	}

	public LocalDateTime getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	public LocalDateTime getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(LocalDateTime dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	@Override
	public String toString() {
		return "Payroll [id=" + id + ", employee=" + employee + ", payPeriodStart=" + payPeriodStart + ", payPeriodEnd="
				+ payPeriodEnd + ", basicSalary=" + basicSalary + ", allowances=" + allowances + ", deductions="
				+ deductions + ", netSalary=" + netSalary + ", status=" + status + ", dateCreated=" + dateCreated
				+ ", dateUpdated=" + dateUpdated + "]";
	}

    // Constructors, Getters, and Setters
    
}