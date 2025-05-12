package com.example.employee.application.port.out;

import com.example.employee.domain.model.Employee;
import com.example.employee.domain.model.Page;
import com.example.employee.domain.model.PageRequest;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepositoryPort {

    Employee save(Employee employee);

    List<Employee> saveAll(List<Employee> employees);

    Optional<Employee> findById(Long id);

    Page<Employee> findAll(PageRequest pageRequest);

    boolean deleteById(Long id);

    boolean existsById(Long id);

}
