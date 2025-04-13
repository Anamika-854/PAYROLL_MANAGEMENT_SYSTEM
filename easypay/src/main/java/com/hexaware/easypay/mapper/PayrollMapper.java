package com.hexaware.easypay.mapper;

import com.hexaware.easypay.dto.PayrollDTO;
import com.hexaware.easypay.model.Payroll;

public class PayrollMapper {
    public static PayrollDTO mapToPayrollDTO(Payroll payroll) {
        PayrollDTO dto = new PayrollDTO();
        dto.setId(payroll.getId());
        dto.setEmployeeId(payroll.getEmployee().getId());
        dto.setPayPeriodStart(payroll.getPayPeriodStart());
        dto.setPayPeriodEnd(payroll.getPayPeriodEnd());
        dto.setBasicSalary(payroll.getBasicSalary());
        dto.setAllowances(payroll.getAllowances());
        dto.setDeductions(payroll.getDeductions());
        dto.setNetSalary(payroll.getNetSalary());
        dto.setStatus(payroll.getStatus());
        return dto;
    }

    public static Payroll mapToPayroll(PayrollDTO dto) {
        Payroll payroll = new Payroll();
        payroll.setPayPeriodStart(dto.getPayPeriodStart());
        payroll.setPayPeriodEnd(dto.getPayPeriodEnd());
        payroll.setBasicSalary(dto.getBasicSalary());
        payroll.setAllowances(dto.getAllowances());
        payroll.setDeductions(dto.getDeductions());
        payroll.setNetSalary(dto.getNetSalary());
        payroll.setStatus(dto.getStatus());
        return payroll;
    }
}