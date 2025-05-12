package com.example.employee.application.port.service;

import com.example.employee.application.port.in.CreateEmployeeUseCase;
import com.example.employee.application.port.in.DeleteEmployeeUseCase;
import com.example.employee.application.port.in.GetEmployeeUseCase;
import com.example.employee.application.port.in.UpdateEmployeeUseCase;
import com.example.employee.application.port.out.EmployeeRepositoryPort;
import com.example.employee.domain.model.Employee;
import com.example.employee.domain.model.Page;
import com.example.employee.domain.model.PageRequest;

import java.util.List;
import java.util.Optional;

public class EmployeeService implements CreateEmployeeUseCase, GetEmployeeUseCase, UpdateEmployeeUseCase, DeleteEmployeeUseCase {

    private final EmployeeRepositoryPort employeeRepositoryPort;

    public EmployeeService(EmployeeRepositoryPort employeeRepositoryPort) {
        this.employeeRepositoryPort = employeeRepositoryPort;
    }

    @Override
    public List<Employee> createEmployees(List<Employee> employees) {
        return employeeRepositoryPort.saveAll(employees);
    }

    @Override
    public boolean deleteEmployeeById(Long id) {
        if (!employeeRepositoryPort.existsById(id)) {
            return false;
        }
        return employeeRepositoryPort.deleteById(id);
    }

    @Override
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepositoryPort.findById(id);
    }

    @Override
    public Page<Employee> getAllEmployees(PageRequest pageRequest) {
        return employeeRepositoryPort.findAll(pageRequest);
    }

    @Override
    public Optional<Employee> updateEmployee(Long id, Employee employeeDetails) {
        if (!employeeRepositoryPort.existsById(id)) {
            return Optional.empty();
        }
        employeeDetails.setId(id);
        return Optional.of(employeeRepositoryPort.save(employeeDetails));
    }
}
