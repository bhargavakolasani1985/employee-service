package com.kbc.practice.emp.service;

import com.kbc.practice.emp.exception.EmployeeNotFoundException;
import com.kbc.practice.emp.exception.EmployeeValidationException;
import com.kbc.practice.emp.model.Employee;

import java.util.List;

public interface IEmployeeService {

    List<Employee> getEmployees();

    Employee getEmployeeById(long id);

    Employee createEmployee(Employee employee) throws EmployeeValidationException;

    boolean updateEmployee(long id, Employee employee) throws EmployeeValidationException;

    boolean deleteEmployeeById(long id) throws EmployeeNotFoundException, EmployeeValidationException;

}
