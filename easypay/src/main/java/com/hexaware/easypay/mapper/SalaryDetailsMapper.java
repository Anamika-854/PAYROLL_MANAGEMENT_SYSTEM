package com.hexaware.easypay.mapper;

import com.hexaware.easypay.dto.SalaryDetailsDTO;
import com.hexaware.easypay.model.SalaryDetails;

public class SalaryDetailsMapper {
    
    public static SalaryDetailsDTO mapToDTO(SalaryDetails salaryDetails) {
        SalaryDetailsDTO dto = new SalaryDetailsDTO();
        dto.setId(salaryDetails.getId());
        dto.setEmployeeId(salaryDetails.getEmployee().getId());
        dto.setBasicSalary(salaryDetails.getBasicSalary());
        dto.setHouseRentAllowance(salaryDetails.getHouseRentAllowance());
        dto.setDearnessAllowance(salaryDetails.getDearnessAllowance());
        dto.setConveyanceAllowance(salaryDetails.getConveyanceAllowance());
        dto.setMedicalAllowance(salaryDetails.getMedicalAllowance());
        dto.setSpecialAllowance(salaryDetails.getSpecialAllowance());
        dto.setProvidentFund(salaryDetails.getProvidentFund());
        dto.setProfessionalTax(salaryDetails.getProfessionalTax());
        dto.setIncomeTax(salaryDetails.getIncomeTax());
        dto.setInsurancePremium(salaryDetails.getInsurancePremium());
        dto.setGrossSalary(salaryDetails.getGrossSalary());
        dto.setTotalDeductions(salaryDetails.getTotalDeductions());
        dto.setNetSalary(salaryDetails.getNetSalary());
        dto.setBankName(salaryDetails.getBankName());
        dto.setAccountNumber(salaryDetails.getAccountNumber());
        dto.setIfscCode(salaryDetails.getIfscCode());
        return dto;
    }

    public static SalaryDetails mapToEntity(SalaryDetailsDTO dto) {
        SalaryDetails salaryDetails = new SalaryDetails();
        salaryDetails.setBasicSalary(dto.getBasicSalary());
        salaryDetails.setHouseRentAllowance(dto.getHouseRentAllowance());
        salaryDetails.setDearnessAllowance(dto.getDearnessAllowance());
        salaryDetails.setConveyanceAllowance(dto.getConveyanceAllowance());
        salaryDetails.setMedicalAllowance(dto.getMedicalAllowance());
        salaryDetails.setSpecialAllowance(dto.getSpecialAllowance());
        salaryDetails.setProvidentFund(dto.getProvidentFund());
        salaryDetails.setProfessionalTax(dto.getProfessionalTax());
        salaryDetails.setIncomeTax(dto.getIncomeTax());
        salaryDetails.setInsurancePremium(dto.getInsurancePremium());
        salaryDetails.setBankName(dto.getBankName());
        salaryDetails.setAccountNumber(dto.getAccountNumber());
        salaryDetails.setIfscCode(dto.getIfscCode());
        return salaryDetails;
    }
}