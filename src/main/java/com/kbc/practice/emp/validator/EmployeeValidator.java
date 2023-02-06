package com.kbc.practice.emp.validator;

import com.kbc.practice.emp.model.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeValidator {

    public boolean isValid(Employee employee) {
        boolean isValid = false;
        if(employee != null){
            if(employee.getName() != null && employee.getDeptId() != null){
                isValid = true;
            }
        }
        return isValid;
    }
}
