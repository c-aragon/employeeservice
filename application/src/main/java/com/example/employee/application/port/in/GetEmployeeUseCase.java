package com.example.employee.application.port.in;

import com.example.employee.domain.model.Employee;
import com.example.employee.domain.model.Page;
import com.example.employee.domain.model.PageRequest;

import java.util.Optional;

public interface GetEmployeeUseCase {

    Optional<Employee> getEmployeeById(Long id);

    Page<Employee> getAllEmployees(PageRequest pageRequest);

}
