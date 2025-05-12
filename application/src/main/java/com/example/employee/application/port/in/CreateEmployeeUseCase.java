package com.example.employee.application.port.in;

import com.example.employee.domain.model.Employee;

import java.util.List;

public interface CreateEmployeeUseCase {

    List<Employee> createEmployees(List<Employee> employees);

}
