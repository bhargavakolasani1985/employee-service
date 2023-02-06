package com.kbc.practice.emp.service;

import com.kbc.practice.emp.dao.domain.EmployeeEntity;
import com.kbc.practice.emp.dao.repository.EmployeeRepository;
import com.kbc.practice.emp.exception.EmployeeNotFoundException;
import com.kbc.practice.emp.exception.EmployeeValidationException;
import com.kbc.practice.emp.model.Employee;
import com.kbc.practice.emp.validator.EmployeeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService implements IEmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    EmployeeValidator employeeValidator;

    @Override
    public List<Employee> getEmployees() {
        final List<Employee> employeeList = new ArrayList<>();
        List<EmployeeEntity> employeeEntityList = employeeRepository.findAll();
        if (employeeEntityList != null && employeeEntityList.size() > 0) {
            employeeEntityList.forEach(employeeEntity -> {
                Employee employee = Employee.builder()
                        .id(employeeEntity.getId())
                        .name(employeeEntity.getName())
                        .role(employeeEntity.getRole())
                        .salary(employeeEntity.getSalary())
                        .deptId(employeeEntity.getDeptId())
                        .build();
                employeeList.add(employee);
            });
        }
        return employeeList;
    }

    @Override
    public Employee getEmployeeById(long id) {
        Employee employee = null;
        Optional<EmployeeEntity> employeeEntityObj = employeeRepository.findById(id);
        if (employeeEntityObj.isPresent()) {
            EmployeeEntity employeeEntity = employeeEntityObj.get();
            employee = Employee.builder()
                    .id(employeeEntity.getId())
                    .name(employeeEntity.getName())
                    .role(employeeEntity.getRole())
                    .salary(employeeEntity.getSalary())
                    .deptId(employeeEntity.getDeptId())
                    .build();
        }
        return employee;
    }

    @Override
    public Employee createEmployee(Employee employee) throws EmployeeValidationException {
        if (employeeValidator.isValid(employee)) {
            Employee createdEmployee = null;
            EmployeeEntity newEmployee = employeeRepository.save(
                    EmployeeEntity.builder()
                            .name(employee.getName())
                            .role(employee.getRole())
                            .salary(employee.getSalary())
                            .deptId(employee.getDeptId())
                            .build()
            );
            if (newEmployee != null && newEmployee.getId() > 0) {
                createdEmployee = Employee.builder()
                        .id(newEmployee.getId())
                        .name(newEmployee.getName())
                        .role(newEmployee.getRole())
                        .salary(newEmployee.getSalary())
                        .deptId(newEmployee.getDeptId())
                        .build();
            }
            return createdEmployee;
        } else {
            throw new EmployeeValidationException("Invalid Input supplied");
        }
    }

    @Override
    public boolean updateEmployee(long id, Employee employee) throws EmployeeValidationException {
        boolean isUpdateSuccess = false;
        if (employeeValidator.isValid(employee)) {
            Optional<EmployeeEntity> existingEmployeeObj = employeeRepository.findById(id);
            if (existingEmployeeObj.isPresent()) {
                EmployeeEntity existingEmployee = existingEmployeeObj.get();
                existingEmployee.setName(employee.getName());
                existingEmployee.setRole(employee.getRole());
                existingEmployee.setSalary(employee.getSalary());
                existingEmployee.setDeptId(employee.getDeptId());
                employeeRepository.save(existingEmployee);
                isUpdateSuccess = true;
            }
            return isUpdateSuccess;
        } else {
            throw new EmployeeValidationException("Invalid Input supplied");
        }
    }

    @Override
    public boolean deleteEmployeeById(long id) throws EmployeeNotFoundException, EmployeeValidationException {
        if(id > 0){
            boolean isUpdateSuccess = false;
            Optional<EmployeeEntity> existingEmployeeObj = employeeRepository.findById(id);
            if (existingEmployeeObj.isPresent()) {
                employeeRepository.deleteById(id);
                isUpdateSuccess = true;
                return isUpdateSuccess;
            } else {
                throw new EmployeeNotFoundException("No Employee data found for given id");
            }
        } else {
            throw new EmployeeValidationException("Invalid Input supplied");
        }
    }

}
