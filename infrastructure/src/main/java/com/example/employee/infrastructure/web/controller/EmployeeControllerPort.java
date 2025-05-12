package com.example.employee.infrastructure.web.controller;

import com.example.employee.domain.model.Page;
import com.example.employee.infrastructure.web.dto.request.EmployeeRequest;
import com.example.employee.infrastructure.web.dto.request.UpdateEmployeeRequest;
import com.example.employee.infrastructure.web.dto.response.EmployeeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Employee Management", description = "APIs for managing employees")
public interface EmployeeControllerPort {

    @Operation(summary = "Create new employees")
    @ApiResponse(responseCode = "201", description = "Employees created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    ResponseEntity<List<EmployeeResponse>> createEmployees(@Valid List<EmployeeRequest> employeeRequests);

    @Operation(summary = "Get all employees")
    @ApiResponse(responseCode = "200", description = "List of employees retrieved successfully")
    ResponseEntity<Page<EmployeeResponse>> getAllEmployees(int page,
                                                           int size,
                                                           String sortBy,
                                                           String direction);

    @Operation(summary = "Update an employee")
    @ApiResponse(responseCode = "200", description = "Employee updated successfully")
    @ApiResponse(responseCode = "404", description = "Employee not found")
    ResponseEntity<EmployeeResponse> updateEmployee(Long id, @Valid UpdateEmployeeRequest updateRequest);

    @Operation(summary = "Delete an employee")
    @ApiResponse(responseCode = "204", description = "Employee deleted successfully")
    @ApiResponse(responseCode = "404", description = "Employee not found")
    ResponseEntity<Void> deleteEmployee(Long id);

}
