package com.hexaware.easypay.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.hexaware.easypay.customvalidator.LeaveRequestValidation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@LeaveRequestValidation
@Table(name = "leave_requests")
public class LeaveRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
    
    @Column(nullable = false)
    private LocalDate startDate;
    
    @Column(nullable = false)
    private LocalDate endDate;
    
    @Column(nullable = false)
    private String leaveType;
    
    private String reason;
    
    @Column(nullable = false)
    private String status; // PENDING, APPROVED, REJECTED
    
    @ManyToOne
    @JoinColumn(name = "approved_by")
    private Employee approvedBy;
    
    @CreationTimestamp
    private LocalDateTime dateCreated;
    
    @UpdateTimestamp
    private LocalDateTime dateUpdated;

	public LeaveRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LeaveRequest(Long id, Employee employee, LocalDate startDate, LocalDate endDate, String leaveType,
			String reason, String status, Employee approvedBy, LocalDateTime dateCreated, LocalDateTime dateUpdated) {
		super();
		this.id = id;
		this.employee = employee;
		this.startDate = startDate;
		this.endDate = endDate;
		this.leaveType = leaveType;
		this.reason = reason;
		this.status = status;
		this.approvedBy = approvedBy;
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

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Employee getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(Employee approvedBy) {
		this.approvedBy = approvedBy;
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
		return "LeaveRequest [id=" + id + ", employee=" + employee + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", leaveType=" + leaveType + ", reason=" + reason + ", status=" + status + ", approvedBy="
				+ approvedBy + ", dateCreated=" + dateCreated + ", dateUpdated=" + dateUpdated + "]";
	}

    // Constructors, Getters, and Setters
    
}