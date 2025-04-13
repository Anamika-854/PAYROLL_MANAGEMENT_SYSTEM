package com.hexaware.easypay.mapper;

import com.hexaware.easypay.dto.EmployeeDTO;
import com.hexaware.easypay.model.Employee;

public class EmployeeMapper {
    public static EmployeeDTO mapToEmployeeDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setEmail(employee.getEmail());
        dto.setPhoneNumber(employee.getPhoneNumber());
        dto.setDepartment(employee.getDepartment());
        dto.setDesignation(employee.getDesignation());
        dto.setJoiningDate(employee.getJoiningDate());
        dto.setBasicSalary(employee.getBasicSalary());
        dto.setManagerId(employee.getManager() != null ? employee.getManager().getId() : null);
        dto.setActive(employee.isActive());
        return dto;
    }

    public static Employee mapToEmployee(EmployeeDTO dto) {
        Employee employee = new Employee();
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmail(dto.getEmail());
        employee.setPhoneNumber(dto.getPhoneNumber());
        employee.setDepartment(dto.getDepartment());
        employee.setDesignation(dto.getDesignation());
        employee.setJoiningDate(dto.getJoiningDate());
        employee.setBasicSalary(dto.getBasicSalary());
        employee.setActive(dto.isActive());
        return employee;
    }
}