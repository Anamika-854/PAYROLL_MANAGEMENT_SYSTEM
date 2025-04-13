package com.hexaware.easypay.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hexaware.easypay.customexceptions.ResourceNotFoundException;
import com.hexaware.easypay.dto.EmployeeDTO;
import com.hexaware.easypay.model.Employee;
import com.hexaware.easypay.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements IEmployeeService {
    
    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public boolean verifyEmployeeDetails(String employeeCode, String email) {
        try {
            Optional<Employee> employee = employeeRepository.findByEmployeeCodeAndEmail(employeeCode, email);
            return employee.isPresent() && employee.get().isActive();
        } catch (Exception e) {
            logger.error("Error verifying employee details: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public Optional<Employee> findByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) {
        try {
            if (employeeRepository.existsByEmail(employeeDTO.getEmail())) {
                throw new IllegalArgumentException("Email already exists: " + employeeDTO.getEmail());
            }

            Employee employee = modelMapper.map(employeeDTO, Employee.class);
            String employeeCode = EmployeeCodeGenerator.generateEmployeeCode();
            employee.setEmployeeCode(employeeCode);
            Employee savedEmployee = employeeRepository.save(employee);
            return modelMapper.map(savedEmployee, EmployeeDTO.class);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error creating employee: {}", e.getMessage());
            throw new RuntimeException("Error creating employee: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) throws ResourceNotFoundException {
        try {
            Employee existingEmployee = employeeRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));

            if (employeeDTO.getFirstName() != null) {
                existingEmployee.setFirstName(employeeDTO.getFirstName());
            }
            if (employeeDTO.getLastName() != null) {
                existingEmployee.setLastName(employeeDTO.getLastName());
            }
            if (employeeDTO.getEmail() != null) {
                existingEmployee.setEmail(employeeDTO.getEmail());
            }
            if (employeeDTO.getPhoneNumber() != null) {
                existingEmployee.setPhoneNumber(employeeDTO.getPhoneNumber());
            }
            if (employeeDTO.getDepartment() != null) {
                existingEmployee.setDepartment(employeeDTO.getDepartment());
            }
            if (employeeDTO.getDesignation() != null) {
                existingEmployee.setDesignation(employeeDTO.getDesignation());
            }
            if (employeeDTO.getBasicSalary() != null) {
                existingEmployee.setBasicSalary(employeeDTO.getBasicSalary());
            }
            if (employeeDTO.getJoiningDate() != null) {
                existingEmployee.setJoiningDate(employeeDTO.getJoiningDate());
            }

            Employee updatedEmployee = employeeRepository.save(existingEmployee);
            return modelMapper.map(updatedEmployee, EmployeeDTO.class);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error updating employee: {}", e.getMessage());
            throw new RuntimeException("Error updating employee: " + e.getMessage());
        }
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) throws ResourceNotFoundException {
        try {
            Employee employee = employeeRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
            return modelMapper.map(employee, EmployeeDTO.class);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error retrieving employee: {}", e.getMessage());
            throw new RuntimeException("Error retrieving employee: " + e.getMessage());
        }
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        try {
            List<Employee> employees = employeeRepository.findAll();
            return employees.stream()
                    .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error retrieving employees: {}", e.getMessage());
            throw new RuntimeException("Error retrieving employees: " + e.getMessage());
        }
    }

    @Override
    public List<EmployeeDTO> getActiveEmployees() {
        try {
            List<Employee> activeEmployees = employeeRepository.findByActiveTrue();
            return activeEmployees.stream()
                    .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error retrieving active employees: {}", e.getMessage());
            throw new RuntimeException("Error retrieving active employees: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) throws ResourceNotFoundException {
        try {
            Employee employee = employeeRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
            employee.setActive(false);
            employeeRepository.save(employee);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error deleting employee: {}", e.getMessage());
            throw new RuntimeException("Error deleting employee: " + e.getMessage());
        }
    }

    @Override
    public List<EmployeeDTO> getEmployeesByManager(Long managerId) {
        try {
            List<Employee> employees = employeeRepository.findByManagerId(managerId);
            return employees.stream()
                    .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error retrieving employees by manager: {}", e.getMessage());
            throw new RuntimeException("Error retrieving employees by manager: " + e.getMessage());
        }
    }
}