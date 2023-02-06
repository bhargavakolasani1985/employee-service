package com.kbc.practice.emp.controller;

import com.kbc.practice.emp.dao.domain.EmployeeEntity;
import com.kbc.practice.emp.dao.repository.EmployeeRepository;
import com.kbc.practice.emp.exception.EmployeeNotFoundException;
import com.kbc.practice.emp.exception.EmployeeValidationException;
import com.kbc.practice.emp.model.Employee;
import com.kbc.practice.emp.model.ServiceResponse;
import com.kbc.practice.emp.service.IEmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "http://localhost:3000") //open for specific port
@CrossOrigin() // open for all ports
@RestController
public class EmployeeController {

    Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    IEmployeeService employeeService;

    /**
     * Get all the employees
     *
     * @return ResponseEntity
     */
    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getEmployees() {
        try {
            List<Employee> empList = employeeService.getEmployees();
            if (empList != null && empList.size() > 0) {
                logger.info("{}", "Data found");
                return new ResponseEntity<>(empList, HttpStatus.OK);
            } else {
                logger.error("{}", "No Data found");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Failed to get employees,", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get the employee by id
     *
     * @param id
     * @return ResponseEntity
     */
    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long id) {
        try {
            Employee employee = employeeService.getEmployeeById(id);
            if (employee != null) {
                return new ResponseEntity<>(employee, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Create new employee
     *
     * @param employee
     * @return ResponseEntity
     */
    @PostMapping("/employees")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        try {
            Employee newEmployee = employeeService.createEmployee(employee);
            return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
        } catch (EmployeeValidationException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update EmployeeEntity record by using it's id
     *
     * @param id
     * @param employee
     * @return
     */
    @PutMapping("/employee/{id}")
    public ResponseEntity<ServiceResponse> updateEmployee(@PathVariable("id") long id, @RequestBody Employee employee) {
        try {
            boolean isUpdateSuccess = employeeService.updateEmployee(id, employee);
            return new ResponseEntity<>(
                    new ServiceResponse(isUpdateSuccess, "Successfully updated given Employee"),
                    HttpStatus.OK);
        } catch (EmployeeValidationException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete EmployeeEntity by Id
     *
     * @param id
     * @return ResponseEntity
     */
    @DeleteMapping("/employee/{id}")
    public ResponseEntity<ServiceResponse> deleteEmployeeById(@PathVariable("id") long id) {
        try {
            boolean isUpdateSuccess = employeeService.deleteEmployeeById(id);
            return new ResponseEntity<>(
                    new ServiceResponse(isUpdateSuccess, "Successfully deleted given Employee"),
                    HttpStatus.OK);
        } catch (EmployeeValidationException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (EmployeeNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
