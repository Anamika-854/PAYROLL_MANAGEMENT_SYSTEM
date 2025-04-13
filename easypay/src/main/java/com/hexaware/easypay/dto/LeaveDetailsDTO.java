package com.hexaware.easypay.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class LeaveDetailsDTO {
    private Long id;
    
    @NotNull(message = "Employee ID is required")
    private Long employeeId;
    
    @PositiveOrZero(message = "Leave count cannot be negative")
    private Integer casualLeavesTotal;
    private Integer casualLeavesUsed;
    
    @PositiveOrZero(message = "Leave count cannot be negative")
    private Integer sickLeavesTotal;
    private Integer sickLeavesUsed;
    
    @PositiveOrZero(message = "Leave count cannot be negative")
    private Integer paidLeavesTotal;
    private Integer paidLeavesUsed;
    
    @PositiveOrZero(message = "Leave count cannot be negative")
    private Integer carryForwardLeavesTotal;
    private Integer carryForwardLeavesUsed;
    
    @PositiveOrZero(message = "Leave count cannot be negative")
    private Integer maternityLeavesTotal;
    private Integer maternityLeavesUsed;
    
    @PositiveOrZero(message = "Leave count cannot be negative")
    private Integer paternityLeavesTotal;
    private Integer paternityLeavesUsed;
    
    @PositiveOrZero(message = "Leave count cannot be negative")
    private Integer marriageLeavesTotal;
    private Integer marriageLeavesUsed;
    
    @PositiveOrZero(message = "Leave count cannot be negative")
    private Integer compensatoryLeavesEarned;
    private Integer compensatoryLeavesUsed;
	public LeaveDetailsDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LeaveDetailsDTO(Long id, @NotNull(message = "Employee ID is required") Long employeeId,
			@PositiveOrZero(message = "Leave count cannot be negative") Integer casualLeavesTotal,
			Integer casualLeavesUsed,
			@PositiveOrZero(message = "Leave count cannot be negative") Integer sickLeavesTotal, Integer sickLeavesUsed,
			@PositiveOrZero(message = "Leave count cannot be negative") Integer paidLeavesTotal, Integer paidLeavesUsed,
			@PositiveOrZero(message = "Leave count cannot be negative") Integer carryForwardLeavesTotal,
			Integer carryForwardLeavesUsed,
			@PositiveOrZero(message = "Leave count cannot be negative") Integer maternityLeavesTotal,
			Integer maternityLeavesUsed,
			@PositiveOrZero(message = "Leave count cannot be negative") Integer paternityLeavesTotal,
			Integer paternityLeavesUsed,
			@PositiveOrZero(message = "Leave count cannot be negative") Integer marriageLeavesTotal,
			Integer marriageLeavesUsed,
			@PositiveOrZero(message = "Leave count cannot be negative") Integer compensatoryLeavesEarned,
			Integer compensatoryLeavesUsed) {
		super();
		this.id = id;
		this.employeeId = employeeId;
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
	@Override
	public String toString() {
		return "LeaveDetailsDTO [id=" + id + ", employeeId=" + employeeId + ", casualLeavesTotal=" + casualLeavesTotal
				+ ", casualLeavesUsed=" + casualLeavesUsed + ", sickLeavesTotal=" + sickLeavesTotal
				+ ", sickLeavesUsed=" + sickLeavesUsed + ", paidLeavesTotal=" + paidLeavesTotal + ", paidLeavesUsed="
				+ paidLeavesUsed + ", carryForwardLeavesTotal=" + carryForwardLeavesTotal + ", carryForwardLeavesUsed="
				+ carryForwardLeavesUsed + ", maternityLeavesTotal=" + maternityLeavesTotal + ", maternityLeavesUsed="
				+ maternityLeavesUsed + ", paternityLeavesTotal=" + paternityLeavesTotal + ", paternityLeavesUsed="
				+ paternityLeavesUsed + ", marriageLeavesTotal=" + marriageLeavesTotal + ", marriageLeavesUsed="
				+ marriageLeavesUsed + ", compensatoryLeavesEarned=" + compensatoryLeavesEarned
				+ ", compensatoryLeavesUsed=" + compensatoryLeavesUsed + "]";
	}
    
    
}