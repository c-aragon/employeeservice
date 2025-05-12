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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    private CreateEmployeeUseCase createEmployeeUseCase;

    @Mock
    private GetEmployeeUseCase getEmployeeUseCase;

    @Mock
    private UpdateEmployeeUseCase updateEmployeeUseCase;

    @Mock
    private DeleteEmployeeUseCase deleteEmployeeUseCase;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeController employeeController;

    private EmployeeRequest employeeRequest;
    private Employee employee;
    private EmployeeResponse employeeResponse;

    @BeforeEach
    void setUp() {
        employeeRequest = new EmployeeRequest();
        employeeRequest.setFirstName("John");
        employeeRequest.setLastName("Doe");
        employeeRequest.setBirthDate(LocalDate.of(1990, 1, 1));

        employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setBirthDate(LocalDate.of(1990, 1, 1));

        employeeResponse = new EmployeeResponse();
        employeeResponse.setId(1L);
        employeeResponse.setFirstName("John");
        employeeResponse.setLastName("Doe");
        employeeResponse.setBirthDate(LocalDate.of(1990, 1, 1));
    }

    @Test
    void createEmployees_Success() {
        // Arrange
        List<EmployeeRequest> requests = Collections.singletonList(employeeRequest);
        List<Employee> employees = Collections.singletonList(employee);
        List<EmployeeResponse> responses = Collections.singletonList(employeeResponse);

        when(employeeMapper.toDomain(any(EmployeeRequest.class))).thenReturn(employee);
        when(createEmployeeUseCase.createEmployees(any())).thenReturn(employees);
        when(employeeMapper.toResponse(any(Employee.class))).thenReturn(employeeResponse);

        // Act
        ResponseEntity<List<EmployeeResponse>> response = employeeController.createEmployees(requests);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responses, response.getBody());
        verify(createEmployeeUseCase).createEmployees(any());
    }

    @Test
    void createEmployees_Empty() {
        // Arrange
        List<EmployeeRequest> requests = new ArrayList<>();

        // Act & Assert
        assertThrows(InvalidEmployeeDataException.class, () ->
                employeeController.createEmployees(requests)
        );
    }

    @Test
    void createEmployees_Null() {
        assertThrows(InvalidEmployeeDataException.class, () ->
                employeeController.createEmployees(null)
        );
    }

    @Test
    void getAllEmployees_Success() {
        // Arrange
        Page<Employee> employeePage = new Page<>(Collections.singletonList(employee), 0, 10, 1, 1, true);

        when(getEmployeeUseCase.getAllEmployees(any(PageRequest.class))).thenReturn(employeePage);
        when(employeeMapper.toResponse(any(Employee.class))).thenReturn(employeeResponse);

        // Act
        ResponseEntity<Page<EmployeeResponse>> response = employeeController.getAllEmployees(0, 10, "id", "asc");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getContent().size());
    }

    @Test
    void updateEmployee_Success() {
        // Arrange
        Long employeeId = 1L;
        UpdateEmployeeRequest updateRequest = new UpdateEmployeeRequest();
        updateRequest.setFirstName("Updated");

        when(getEmployeeUseCase.getEmployeeById(employeeId)).thenReturn(Optional.of(employee));
        when(updateEmployeeUseCase.updateEmployee(eq(employeeId), any(Employee.class)))
                .thenReturn(Optional.of(employee));
        when(employeeMapper.toResponse(any(Employee.class))).thenReturn(employeeResponse);

        // Act
        ResponseEntity<EmployeeResponse> response = employeeController.updateEmployee(employeeId, updateRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(updateEmployeeUseCase).updateEmployee(eq(employeeId), any(Employee.class));
    }

    @Test
    void updateEmployee_NotFound() {
        // Arrange
        Long employeeId = 1L;
        UpdateEmployeeRequest updateRequest = new UpdateEmployeeRequest();

        when(getEmployeeUseCase.getEmployeeById(employeeId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EmployeeNotFoundException.class, () -> 
            employeeController.updateEmployee(employeeId, updateRequest)
        );
    }

    @Test
    void deleteEmployee_Success() {
        // Arrange
        Long employeeId = 1L;
        when(deleteEmployeeUseCase.deleteEmployeeById(employeeId)).thenReturn(true);

        // Act
        ResponseEntity<Void> response = employeeController.deleteEmployee(employeeId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(deleteEmployeeUseCase).deleteEmployeeById(employeeId);
    }

    @Test
    void deleteEmployee_NotFound() {
        // Arrange
        Long employeeId = 1L;
        when(deleteEmployeeUseCase.deleteEmployeeById(employeeId)).thenReturn(false);

        // Act & Assert
        assertThrows(EmployeeNotFoundException.class, () -> 
            employeeController.deleteEmployee(employeeId)
        );
    }
} 