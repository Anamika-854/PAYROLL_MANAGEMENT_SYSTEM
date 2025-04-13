package com.hexaware.easypay.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.hexaware.easypay.customvalidator.EmployeeValidation;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

@EmployeeValidation
public class EmployeeDTO {
	private Long id;

	private String employeeCode;

	@NotBlank(message = "First name is required")
	@Pattern(regexp = "^[A-Za-z\\s]{2,30}$", message = "First name should be 2-30 characters long and contain only letters")
	private String firstName;

	@NotBlank(message = "Last name is required")
	@Pattern(regexp = "^[A-Za-z\\s]{2,30}$", message = "Last name should be 2-30 characters long and contain only letters")
	private String lastName;

	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	@Pattern(regexp = "^[A-Za-z0-9+_.-]+@hexaware\\.com$", message = "Email must be a valid Hexaware email address")
	private String email;

	@Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
	private String phoneNumber;

	@NotBlank(message = "Department is required")
	private String department;

	@NotBlank(message = "Designation is required")
	private String designation;

	@NotNull(message = "Joining date is required")
	@PastOrPresent(message = "Joining date cannot be in the future")
	private LocalDate joiningDate;

	@NotNull(message = "Basic salary is required")
	@Positive(message = "Basic salary must be positive")
	@DecimalMin(value = "10000.00", message = "Basic salary must be at least ₹10,000")
	private BigDecimal basicSalary;

	private Long managerId;
	private boolean active = true;

	public EmployeeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmployeeDTO(Long id, @NotEmpty(message = "Employee code is required") String employeeCode,
			@NotEmpty(message = "First name is required") String firstName,
			@NotEmpty(message = "Last name is required") String lastName,
			@NotEmpty(message = "Email is required") @Email(message = "Invalid email format") String email,
			String phoneNumber, String department, String designation,
			@NotNull(message = "Joining date is required") LocalDate joiningDate,
			@Positive(message = "Basic salary must be positive") BigDecimal basicSalary, Long managerId,
			boolean active) {
		super();
		this.id = id;
		this.employeeCode = employeeCode;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.department = department;
		this.designation = designation;
		this.joiningDate = joiningDate;
		this.basicSalary = basicSalary;
		this.managerId = managerId;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public LocalDate getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(LocalDate joiningDate) {
		this.joiningDate = joiningDate;
	}

	public BigDecimal getBasicSalary() {
		return basicSalary;
	}

	public void setBasicSalary(BigDecimal basicSalary) {
		this.basicSalary = basicSalary;
	}

	public Long getManagerId() {
		return managerId;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "EmployeeDTO [id=" + id + ", employeeCode=" + employeeCode + ", firstName=" + firstName + ", lastName="
				+ lastName + ", email=" + email + ", phoneNumber=" + phoneNumber + ", department=" + department
				+ ", designation=" + designation + ", joiningDate=" + joiningDate + ", basicSalary=" + basicSalary
				+ ", managerId=" + managerId + ", active=" + active + "]";
	}

	// Constructors, Getters, and Setters

}