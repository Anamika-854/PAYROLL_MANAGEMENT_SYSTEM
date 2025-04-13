package com.hexaware.easypay.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.hexaware.easypay.customvalidator.SalaryDetailsValidation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@SalaryDetailsValidation
@Table(name = "salary_details")
public class SalaryDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(nullable = false)
    private BigDecimal basicSalary;

    @Column(nullable = false)
    private BigDecimal houseRentAllowance; // HRA

    @Column(nullable = false)
    private BigDecimal dearnessAllowance; // DA

    @Column(nullable = false)
    private BigDecimal conveyanceAllowance;

    @Column(nullable = false)
    private BigDecimal medicalAllowance;

    @Column(nullable = false)
    private BigDecimal specialAllowance;
    
    // Deductions
    @Column(nullable = false)
    private BigDecimal providentFund; // PF

    @Column(nullable = false)
    private BigDecimal professionalTax;

    @Column(nullable = false)
    private BigDecimal incomeTax;

    @Column(nullable = false)
    private BigDecimal insurancePremium;

    // Calculated fields
    @Column(nullable = false)
    private BigDecimal grossSalary;

    @Column(nullable = false)
    private BigDecimal totalDeductions;

    @Column(nullable = false)
    private BigDecimal netSalary;

    private String bankName;
    
    private String accountNumber;
    
    private String ifscCode;

    @CreationTimestamp
    private LocalDateTime dateCreated;

    @UpdateTimestamp
    private LocalDateTime dateUpdated;

	public SalaryDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SalaryDetails(Long id, Employee employee, BigDecimal basicSalary, BigDecimal houseRentAllowance,
			BigDecimal dearnessAllowance, BigDecimal conveyanceAllowance, BigDecimal medicalAllowance,
			BigDecimal specialAllowance, BigDecimal providentFund, BigDecimal professionalTax, BigDecimal incomeTax,
			BigDecimal insurancePremium, BigDecimal grossSalary, BigDecimal totalDeductions, BigDecimal netSalary,
			String bankName, String accountNumber, String ifscCode, LocalDateTime dateCreated,
			LocalDateTime dateUpdated) {
		super();
		this.id = id;
		this.employee = employee;
		this.basicSalary = basicSalary;
		this.houseRentAllowance = houseRentAllowance;
		this.dearnessAllowance = dearnessAllowance;
		this.conveyanceAllowance = conveyanceAllowance;
		this.medicalAllowance = medicalAllowance;
		this.specialAllowance = specialAllowance;
		this.providentFund = providentFund;
		this.professionalTax = professionalTax;
		this.incomeTax = incomeTax;
		this.insurancePremium = insurancePremium;
		this.grossSalary = grossSalary;
		this.totalDeductions = totalDeductions;
		this.netSalary = netSalary;
		this.bankName = bankName;
		this.accountNumber = accountNumber;
		this.ifscCode = ifscCode;
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

	public BigDecimal getBasicSalary() {
		return basicSalary;
	}

	public void setBasicSalary(BigDecimal basicSalary) {
		this.basicSalary = basicSalary;
	}

	public BigDecimal getHouseRentAllowance() {
		return houseRentAllowance;
	}

	public void setHouseRentAllowance(BigDecimal houseRentAllowance) {
		this.houseRentAllowance = houseRentAllowance;
	}

	public BigDecimal getDearnessAllowance() {
		return dearnessAllowance;
	}

	public void setDearnessAllowance(BigDecimal dearnessAllowance) {
		this.dearnessAllowance = dearnessAllowance;
	}

	public BigDecimal getConveyanceAllowance() {
		return conveyanceAllowance;
	}

	public void setConveyanceAllowance(BigDecimal conveyanceAllowance) {
		this.conveyanceAllowance = conveyanceAllowance;
	}

	public BigDecimal getMedicalAllowance() {
		return medicalAllowance;
	}

	public void setMedicalAllowance(BigDecimal medicalAllowance) {
		this.medicalAllowance = medicalAllowance;
	}

	public BigDecimal getSpecialAllowance() {
		return specialAllowance;
	}

	public void setSpecialAllowance(BigDecimal specialAllowance) {
		this.specialAllowance = specialAllowance;
	}

	public BigDecimal getProvidentFund() {
		return providentFund;
	}

	public void setProvidentFund(BigDecimal providentFund) {
		this.providentFund = providentFund;
	}

	public BigDecimal getProfessionalTax() {
		return professionalTax;
	}

	public void setProfessionalTax(BigDecimal professionalTax) {
		this.professionalTax = professionalTax;
	}

	public BigDecimal getIncomeTax() {
		return incomeTax;
	}

	public void setIncomeTax(BigDecimal incomeTax) {
		this.incomeTax = incomeTax;
	}

	public BigDecimal getInsurancePremium() {
		return insurancePremium;
	}

	public void setInsurancePremium(BigDecimal insurancePremium) {
		this.insurancePremium = insurancePremium;
	}

	public BigDecimal getGrossSalary() {
		return grossSalary;
	}

	public void setGrossSalary(BigDecimal grossSalary) {
		this.grossSalary = grossSalary;
	}

	public BigDecimal getTotalDeductions() {
		return totalDeductions;
	}

	public void setTotalDeductions(BigDecimal totalDeductions) {
		this.totalDeductions = totalDeductions;
	}

	public BigDecimal getNetSalary() {
		return netSalary;
	}

	public void setNetSalary(BigDecimal netSalary) {
		this.netSalary = netSalary;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

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
		return "SalaryDetails [id=" + id + ", employee=" + employee + ", basicSalary=" + basicSalary
				+ ", houseRentAllowance=" + houseRentAllowance + ", dearnessAllowance=" + dearnessAllowance
				+ ", conveyanceAllowance=" + conveyanceAllowance + ", medicalAllowance=" + medicalAllowance
				+ ", specialAllowance=" + specialAllowance + ", providentFund=" + providentFund + ", professionalTax="
				+ professionalTax + ", incomeTax=" + incomeTax + ", insurancePremium=" + insurancePremium
				+ ", grossSalary=" + grossSalary + ", totalDeductions=" + totalDeductions + ", netSalary=" + netSalary
				+ ", bankName=" + bankName + ", accountNumber=" + accountNumber + ", ifscCode=" + ifscCode
				+ ", dateCreated=" + dateCreated + ", dateUpdated=" + dateUpdated + "]";
	}
    
    
}