package com.hexaware.easypay.service;

import java.math.BigDecimal;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hexaware.easypay.customexceptions.ResourceNotFoundException;
import com.hexaware.easypay.dto.SalaryDetailsDTO;
import com.hexaware.easypay.model.Employee;
import com.hexaware.easypay.model.SalaryDetails;
import com.hexaware.easypay.repository.EmployeeRepository;
import com.hexaware.easypay.repository.SalaryDetailsRepository;


@Service
public class SalaryDetailsServiceImpl implements ISalaryDetailsService {
	
	@Autowired
    private SalaryDetailsRepository salaryDetailsRepository;
	@Autowired
    private EmployeeRepository employeeRepository;
	@Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public SalaryDetailsDTO createSalaryDetails(SalaryDetailsDTO salaryDetailsDTO) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(salaryDetailsDTO.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", salaryDetailsDTO.getEmployeeId()));

        if (salaryDetailsRepository.findByEmployeeId(salaryDetailsDTO.getEmployeeId()).isPresent()) {
            throw new IllegalStateException("Salary details already exist for this employee");
        }

        SalaryDetails salaryDetails = modelMapper.map(salaryDetailsDTO, SalaryDetails.class);
        salaryDetails.setEmployee(employee);
        calculateSalaryComponents(salaryDetails);

        SalaryDetails savedDetails = salaryDetailsRepository.save(salaryDetails);
        return modelMapper.map(savedDetails, SalaryDetailsDTO.class);
    }

    @Override
    @Transactional
    public SalaryDetailsDTO updateSalaryDetails(Long id, SalaryDetailsDTO salaryDetailsDTO) 
            throws ResourceNotFoundException {
        SalaryDetails existingDetails = salaryDetailsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SalaryDetails", "id", id));

        updateSalaryComponents(existingDetails, salaryDetailsDTO);
        calculateSalaryComponents(existingDetails);

        SalaryDetails updatedDetails = salaryDetailsRepository.save(existingDetails);
        return modelMapper.map(updatedDetails, SalaryDetailsDTO.class);
    }

    @Override
    public SalaryDetailsDTO getSalaryDetailsByEmployeeId(Long employeeId) throws ResourceNotFoundException {
        SalaryDetails salaryDetails = salaryDetailsRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("SalaryDetails", "employeeId", employeeId));
        return modelMapper.map(salaryDetails, SalaryDetailsDTO.class);
    }

    @Override
    @Transactional
    public void calculateSalary(Long employeeId) throws ResourceNotFoundException {
        SalaryDetails salaryDetails = salaryDetailsRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("SalaryDetails", "employeeId", employeeId));

        calculateSalaryComponents(salaryDetails);
        salaryDetailsRepository.save(salaryDetails);
    }

    @Override
    public BigDecimal calculateTotalDeductions(SalaryDetailsDTO dto) {
        return dto.getProvidentFund()
                .add(dto.getProfessionalTax())
                .add(dto.getIncomeTax())
                .add(dto.getInsurancePremium());
    }

    @Override
    public BigDecimal calculateNetSalary(SalaryDetailsDTO dto) {
        return dto.getGrossSalary().subtract(calculateTotalDeductions(dto));
    }

    private void calculateSalaryComponents(SalaryDetails salaryDetails) {
        try {
            BigDecimal basicSalary = salaryDetails.getBasicSalary() != null ? 
                salaryDetails.getBasicSalary() : BigDecimal.ZERO;

            // Calculate allowances with null checks
            BigDecimal hra = basicSalary.multiply(new BigDecimal("0.40"));
            BigDecimal da = basicSalary.multiply(new BigDecimal("0.15"));
            BigDecimal conveyanceAllowance = new BigDecimal("1600");
            BigDecimal medicalAllowance = new BigDecimal("1250");
            BigDecimal specialAllowance = basicSalary.multiply(new BigDecimal("0.10"));

            // Calculate deductions with null checks
            BigDecimal pf = basicSalary.multiply(new BigDecimal("0.12"));
            BigDecimal professionalTax = basicSalary.multiply(new BigDecimal("0.02"));
            BigDecimal incomeTax = salaryDetails.getIncomeTax() != null ? 
                salaryDetails.getIncomeTax() : BigDecimal.ZERO;
            BigDecimal insurancePremium = salaryDetails.getInsurancePremium() != null ? 
                salaryDetails.getInsurancePremium() : BigDecimal.ZERO;

            // Set all values
            salaryDetails.setHouseRentAllowance(hra);
            salaryDetails.setDearnessAllowance(da);
            salaryDetails.setConveyanceAllowance(conveyanceAllowance);
            salaryDetails.setMedicalAllowance(medicalAllowance);
            salaryDetails.setSpecialAllowance(specialAllowance);
            salaryDetails.setProvidentFund(pf);
            salaryDetails.setProfessionalTax(professionalTax);

            // Calculate totals
            BigDecimal totalAllowances = hra.add(da).add(conveyanceAllowance)
                .add(medicalAllowance).add(specialAllowance);
            BigDecimal totalDeductions = pf.add(professionalTax).add(incomeTax).add(insurancePremium);

            salaryDetails.setGrossSalary(basicSalary.add(totalAllowances));
            salaryDetails.setTotalDeductions(totalDeductions);
            salaryDetails.setNetSalary(salaryDetails.getGrossSalary().subtract(totalDeductions));
            
        } catch (Exception e) {
            throw new RuntimeException("Error calculating salary components: " + e.getMessage());
        }
    }

    private void updateSalaryComponents(SalaryDetails existingDetails, SalaryDetailsDTO dto) {
        existingDetails.setBasicSalary(dto.getBasicSalary());
        existingDetails.setIncomeTax(dto.getIncomeTax());
        existingDetails.setInsurancePremium(dto.getInsurancePremium());
        existingDetails.setBankName(dto.getBankName());
        existingDetails.setAccountNumber(dto.getAccountNumber());
        existingDetails.setIfscCode(dto.getIfscCode());
    }
}