package com.hexaware.easypay.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;

public class SalaryDetailsDTO {
    private Long id;
    
    @NotNull(message = "Employee ID is required")
    private Long employeeId;
    
    @Positive(message = "Basic salary must be positive")
    private BigDecimal basicSalary;
    
    @Positive(message = "HRA must be positive")
    private BigDecimal houseRentAllowance;
    
    @Positive(message = "DA must be positive")
    private BigDecimal dearnessAllowance;
    
    @Positive(message = "Conveyance allowance must be positive")
    private BigDecimal conveyanceAllowance;
    
    @Positive(message = "Medical allowance must be positive")
    private BigDecimal medicalAllowance;
    
    @Positive(message = "Special allowance must be positive")
    private BigDecimal specialAllowance;
    
    @Positive(message = "PF must be positive")
    private BigDecimal providentFund;
    
    @Positive(message = "Professional tax must be positive")
    private BigDecimal professionalTax;
    
    @Positive(message = "Income tax must be positive")
    private BigDecimal incomeTax;
    
    @Positive(message = "Insurance premium must be positive")
    private BigDecimal insurancePremium;
    
    private BigDecimal grossSalary;
    private BigDecimal totalDeductions;
    private BigDecimal netSalary;
    
    private String bankName;
    
    @Pattern(regexp = "^[0-9]{9,18}$", message = "Invalid account number")
    private String accountNumber;
    
    @Pattern(regexp = "^[A-Z]{4}0[A-Z0-9]{6}$", message = "Invalid IFSC code")
    private String ifscCode;

	public SalaryDetailsDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SalaryDetailsDTO(Long id, @NotNull(message = "Employee ID is required") Long employeeId,
			@Positive(message = "Basic salary must be positive") BigDecimal basicSalary,
			@Positive(message = "HRA must be positive") BigDecimal houseRentAllowance,
			@Positive(message = "DA must be positive") BigDecimal dearnessAllowance,
			@Positive(message = "Conveyance allowance must be positive") BigDecimal conveyanceAllowance,
			@Positive(message = "Medical allowance must be positive") BigDecimal medicalAllowance,
			@Positive(message = "Special allowance must be positive") BigDecimal specialAllowance,
			@Positive(message = "PF must be positive") BigDecimal providentFund,
			@Positive(message = "Professional tax must be positive") BigDecimal professionalTax,
			@Positive(message = "Income tax must be positive") BigDecimal incomeTax,
			@Positive(message = "Insurance premium must be positive") BigDecimal insurancePremium,
			BigDecimal grossSalary, BigDecimal totalDeductions, BigDecimal netSalary, String bankName,
			@Pattern(regexp = "^[0-9]{9,18}$", message = "Invalid account number") String accountNumber,
			@Pattern(regexp = "^[A-Z]{4}0[A-Z0-9]{6}$", message = "Invalid IFSC code") String ifscCode) {
		super();
		this.id = id;
		this.employeeId = employeeId;
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

	@Override
	public String toString() {
		return "SalaryDetailsDTO [id=" + id + ", employeeId=" + employeeId + ", basicSalary=" + basicSalary
				+ ", houseRentAllowance=" + houseRentAllowance + ", dearnessAllowance=" + dearnessAllowance
				+ ", conveyanceAllowance=" + conveyanceAllowance + ", medicalAllowance=" + medicalAllowance
				+ ", specialAllowance=" + specialAllowance + ", providentFund=" + providentFund + ", professionalTax="
				+ professionalTax + ", incomeTax=" + incomeTax + ", insurancePremium=" + insurancePremium
				+ ", grossSalary=" + grossSalary + ", totalDeductions=" + totalDeductions + ", netSalary=" + netSalary
				+ ", bankName=" + bankName + ", accountNumber=" + accountNumber + ", ifscCode=" + ifscCode + "]";
	}
    
    
}

