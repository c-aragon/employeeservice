package com.example.employee.infrastructure.web.controller;

import com.example.employee.application.port.in.CreateEmployeeUseCase;
import com.example.employee.application.port.in.DeleteEmployeeUseCase;
import com.example.employee.application.port.in.GetEmployeeUseCase;
import com.example.employee.application.port.in.UpdateEmployeeUseCase;
import com.example.employee.domain.exception.EmployeeNotFoundException;
import com.example.employee.domain.exception.InvalidEmployeeDataException;
import com.example.employee.domain.model.Employee;
import com.example.employee.domain.model.Page;
import com.example.employee.domain.model.PageRequest;
import com.example.employee.infrastructure.mapper.EmployeeMapper;
import com.example.employee.infrastructure.web.dto.request.EmployeeRequest;
import com.example.employee.infrastructure.web.dto.request.UpdateEmployeeRequest;
import com.example.employee.infrastructure.web.dto.response.EmployeeResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/employees")
@Slf4j
public class EmployeeController implements EmployeeControllerPort {

    private final CreateEmployeeUseCase createEmployeeUseCase;
    private final GetEmployeeUseCase getEmployeeUseCase;
    private final UpdateEmployeeUseCase updateEmployeeUseCase;
    private final DeleteEmployeeUseCase deleteEmployeeUseCase;
    private final EmployeeMapper employeeMapper;

    public EmployeeController(CreateEmployeeUseCase createEmployeeUseCase,
                              GetEmployeeUseCase getEmployeeUseCase,
                              UpdateEmployeeUseCase updateEmployeeUseCase,
                              DeleteEmployeeUseCase deleteEmployeeUseCase,
                              EmployeeMapper employeeMapper) {
        this.createEmployeeUseCase = createEmployeeUseCase;
        this.getEmployeeUseCase = getEmployeeUseCase;
        this.updateEmployeeUseCase = updateEmployeeUseCase;
        this.deleteEmployeeUseCase = deleteEmployeeUseCase;
        this.employeeMapper = employeeMapper;
    }

    @Override
    @PostMapping
    public ResponseEntity<List<EmployeeResponse>> createEmployees(@Valid @RequestBody List<EmployeeRequest> employeeRequests) {
        if (employeeRequests == null || employeeRequests.isEmpty()) {
            throw new InvalidEmployeeDataException("Employee requests are empty");
        }

        log.info("Received request to create {} employees", employeeRequests.size());

        List<Employee> domainEmployees = mapToDomainEmployees(employeeRequests);
        List<Employee> createdEmployees = createEmployeeUseCase.createEmployees(domainEmployees);
        List<EmployeeResponse> response = mapToResponse(createdEmployees);

        log.info("Successfully created {} employees", createdEmployees.size());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<EmployeeResponse>> getAllEmployees(@RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "10") int size,
                                                                  @RequestParam(defaultValue = "id") String sortBy,
                                                                  @RequestParam(defaultValue = "asc") String direction) {
        PageRequest pageRequest = new PageRequest(page, size, sortBy, direction);

        Page<Employee> employees = getEmployeeUseCase.getAllEmployees(pageRequest);
        Page<EmployeeResponse> response = new Page<>(
                employees.getContent().stream().map(employeeMapper::toResponse).toList(),
                employees.getPageNumber(),
                employees.getPageSize(),
                employees.getTotalElements(),
                employees.getTotalPages(),
                employees.isLast()
        );
        return ResponseEntity.ok(response);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable Long id,@Valid @RequestBody UpdateEmployeeRequest updateRequest) {
        log.info("Received request to update {} id employee", id);

        Employee existingEmployee = getEmployeeUseCase.getEmployeeById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        employeeMapper.updateDomainFromDto(updateRequest, existingEmployee);

        log.info("Successfully update {} id employee", id);

        return updateEmployeeUseCase.updateEmployee(id, existingEmployee)
                .map(employeeMapper::toResponse)
                .map(ResponseEntity::ok).get();
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        log.info("Received request to delete {} id employee", id);
        boolean deleted = deleteEmployeeUseCase.deleteEmployeeById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            throw new EmployeeNotFoundException(id);
        }
    }

    private List<Employee> mapToDomainEmployees(List<EmployeeRequest> requests) {
        return requests.stream()
                .map(employeeMapper::toDomain)
                .collect(Collectors.toList());
    }

    private List<EmployeeResponse> mapToResponse(List<Employee> employees) {
        return employees.stream()
                .map(employeeMapper::toResponse)
                .collect(Collectors.toList());
    }

}
