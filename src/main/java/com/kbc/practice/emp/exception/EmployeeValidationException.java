package com.kbc.practice.emp.exception;

public class EmployeeValidationException extends Exception{

    public EmployeeValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmployeeValidationException(String message) {
        super(message);
    }

    public EmployeeValidationException(Throwable cause) {
        super(cause);
    }

}
