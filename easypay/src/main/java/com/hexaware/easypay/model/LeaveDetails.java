package com.hexaware.easypay.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.hexaware.easypay.customvalidator.LeaveDetailsValidation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@LeaveDetailsValidation
@Table(name = "leave_details")
public class LeaveDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    // Current Year Leaves
    @Column(nullable = false)
    private Integer casualLeavesTotal = 12;  // Fixed per year

    @Column(nullable = false)
    private Integer casualLeavesUsed = 0;

    @Column(nullable = false)
    private Integer sickLeavesTotal = 15;    // Fixed per year

    @Column(nullable = false)
    private Integer sickLeavesUsed = 0;

    @Column(nullable = false)
    private Integer paidLeavesTotal = 15;    // Fixed per year

    @Column(nullable = false)
    private Integer paidLeavesUsed = 0;

    // Carry Forward Leaves from Previous Year
    @Column(nullable = false)
    private Integer carryForwardLeavesTotal = 0;  // Max 30 days

    @Column(nullable = false)
    private Integer carryForwardLeavesUsed = 0;

    // Special Leaves
    @Column(nullable = false)
    private Integer maternityLeavesTotal = 180;   // 6 months

    @Column(nullable = false)
    private Integer maternityLeavesUsed = 0;

    @Column(nullable = false)
    private Integer paternityLeavesTotal = 15;    // 15 days

    @Column(nullable = false)
    private Integer paternityLeavesUsed = 0;

    @Column(nullable = false)
    private Integer marriageLeavesTotal = 15;     // 15 days

    @Column(nullable = false)
    private Integer marriageLeavesUsed = 0;

    private Integer compensatoryLeavesEarned = 0;
    private Integer compensatoryLeavesUsed = 0;

    @CreationTimestamp
    private LocalDateTime dateCreated;

    @UpdateTimestamp
    private LocalDateTime dateUpdated;

	public LeaveDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LeaveDetails(Long id, Employee employee, Integer casualLeavesTotal, Integer casualLeavesUsed,
			Integer sickLeavesTotal, Integer sickLeavesUsed, Integer paidLeavesTotal, Integer paidLeavesUsed,
			Integer carryForwardLeavesTotal, Integer carryForwardLeavesUsed, Integer maternityLeavesTotal,
			Integer maternityLeavesUsed, Integer paternityLeavesTotal, Integer paternityLeavesUsed,
			Integer marriageLeavesTotal, Integer marriageLeavesUsed, Integer compensatoryLeavesEarned,
			Integer compensatoryLeavesUsed, LocalDateTime dateCreated, LocalDateTime dateUpdated) {
		super();
		this.id = id;
		this.employee = employee;
		this.casualLeavesTotal = casualLeavesTotal;
		this.casualLeavesUsed = casualLeavesUsed;
		this.sickLeavesTotal = sickLeavesTotal;
		this.sickLeavesUsed = sickLeavesUsed;
		this.paidLeavesTotal = paidLeavesTotal;
		this.paidLeavesUsed = paidLeavesUsed;
		this.carryForwardLeavesTotal = carryForwardLeavesTotal;
		this.carryForwardLeavesUsed = carryForwardLeavesUsed;
		this.maternityLeavesTotal = maternityLeavesTotal;
		this.maternityLeavesUsed = maternityLeavesUsed;
		this.paternityLeavesTotal = paternityLeavesTotal;
		this.paternityLeavesUsed = paternityLeavesUsed;
		this.marriageLeavesTotal = marriageLeavesTotal;
		this.marriageLeavesUsed = marriageLeavesUsed;
		this.compensatoryLeavesEarned = compensatoryLeavesEarned;
		this.compensatoryLeavesUsed = compensatoryLeavesUsed;
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

	public Integer getCasualLeavesTotal() {
		return casualLeavesTotal;
	}

	public void setCasualLeavesTotal(Integer casualLeavesTotal) {
		this.casualLeavesTotal = casualLeavesTotal;
	}

	public Integer getCasualLeavesUsed() {
		return casualLeavesUsed;
	}

	public void setCasualLeavesUsed(Integer casualLeavesUsed) {
		this.casualLeavesUsed = casualLeavesUsed;
	}

	public Integer getSickLeavesTotal() {
		return sickLeavesTotal;
	}

	public void setSickLeavesTotal(Integer sickLeavesTotal) {
		this.sickLeavesTotal = sickLeavesTotal;
	}

	public Integer getSickLeavesUsed() {
		return sickLeavesUsed;
	}

	public void setSickLeavesUsed(Integer sickLeavesUsed) {
		this.sickLeavesUsed = sickLeavesUsed;
	}

	public Integer getPaidLeavesTotal() {
		return paidLeavesTotal;
	}

	public void setPaidLeavesTotal(Integer paidLeavesTotal) {
		this.paidLeavesTotal = paidLeavesTotal;
	}

	public Integer getPaidLeavesUsed() {
		return paidLeavesUsed;
	}

	public void setPaidLeavesUsed(Integer paidLeavesUsed) {
		this.paidLeavesUsed = paidLeavesUsed;
	}

	public Integer getCarryForwardLeavesTotal() {
		return carryForwardLeavesTotal;
	}

	public void setCarryForwardLeavesTotal(Integer carryForwardLeavesTotal) {
		this.carryForwardLeavesTotal = carryForwardLeavesTotal;
	}

	public Integer getCarryForwardLeavesUsed() {
		return carryForwardLeavesUsed;
	}

	public void setCarryForwardLeavesUsed(Integer carryForwardLeavesUsed) {
		this.carryForwardLeavesUsed = carryForwardLeavesUsed;
	}

	public Integer getMaternityLeavesTotal() {
		return maternityLeavesTotal;
	}

	public void setMaternityLeavesTotal(Integer maternityLeavesTotal) {
		this.maternityLeavesTotal = maternityLeavesTotal;
	}

	public Integer getMaternityLeavesUsed() {
		return maternityLeavesUsed;
	}

	public void setMaternityLeavesUsed(Integer maternityLeavesUsed) {
		this.maternityLeavesUsed = maternityLeavesUsed;
	}

	public Integer getPaternityLeavesTotal() {
		return paternityLeavesTotal;
	}

	public void setPaternityLeavesTotal(Integer paternityLeavesTotal) {
		this.paternityLeavesTotal = paternityLeavesTotal;
	}

	public Integer getPaternityLeavesUsed() {
		return paternityLeavesUsed;
	}

	public void setPaternityLeavesUsed(Integer paternityLeavesUsed) {
		this.paternityLeavesUsed = paternityLeavesUsed;
	}

	public Integer getMarriageLeavesTotal() {
		return marriageLeavesTotal;
	}

	public void setMarriageLeavesTotal(Integer marriageLeavesTotal) {
		this.marriageLeavesTotal = marriageLeavesTotal;
	}

	public Integer getMarriageLeavesUsed() {
		return marriageLeavesUsed;
	}

	public void setMarriageLeavesUsed(Integer marriageLeavesUsed) {
		this.marriageLeavesUsed = marriageLeavesUsed;
	}

	public Integer getCompensatoryLeavesEarned() {
		return compensatoryLeavesEarned;
	}

	public void setCompensatoryLeavesEarned(Integer compensatoryLeavesEarned) {
		this.compensatoryLeavesEarned = compensatoryLeavesEarned;
	}

	public Integer getCompensatoryLeavesUsed() {
		return compensatoryLeavesUsed;
	}

	public void setCompensatoryLeavesUsed(Integer compensatoryLeavesUsed) {
		this.compensatoryLeavesUsed = compensatoryLeavesUsed;
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
		return "LeaveDetails [id=" + id + ", employee=" + employee + ", casualLeavesTotal=" + casualLeavesTotal
				+ ", casualLeavesUsed=" + casualLeavesUsed + ", sickLeavesTotal=" + sickLeavesTotal
				+ ", sickLeavesUsed=" + sickLeavesUsed + ", paidLeavesTotal=" + paidLeavesTotal + ", paidLeavesUsed="
				+ paidLeavesUsed + ", carryForwardLeavesTotal=" + carryForwardLeavesTotal + ", carryForwardLeavesUsed="
				+ carryForwardLeavesUsed + ", maternityLeavesTotal=" + maternityLeavesTotal + ", maternityLeavesUsed="
				+ maternityLeavesUsed + ", paternityLeavesTotal=" + paternityLeavesTotal + ", paternityLeavesUsed="
				+ paternityLeavesUsed + ", marriageLeavesTotal=" + marriageLeavesTotal + ", marriageLeavesUsed="
				+ marriageLeavesUsed + ", compensatoryLeavesEarned=" + compensatoryLeavesEarned
				+ ", compensatoryLeavesUsed=" + compensatoryLeavesUsed + ", dateCreated=" + dateCreated
				+ ", dateUpdated=" + dateUpdated + "]";
	}
    
    
}