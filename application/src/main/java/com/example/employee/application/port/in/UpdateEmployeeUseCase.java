package com.example.employee.application.port.in;

import com.example.employee.domain.model.Employee;

import java.util.Optional;

public interface UpdateEmployeeUseCase {

    Optional<Employee> updateEmployee(Long id, Employee employeeDetails);

}
