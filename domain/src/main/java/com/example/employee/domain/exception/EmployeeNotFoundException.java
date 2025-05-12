package com.example.employee.domain.exception;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(Long id) {
        super(String.format("Employee with id %d not found", id));
    }

    public EmployeeNotFoundException(String message) {
        super(message);
    }

}
