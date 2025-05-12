package com.example.employee.infrastructure.persistence.repository;

import com.example.employee.domain.model.Employee;
import com.example.employee.domain.model.Page;
import com.example.employee.domain.model.PageRequest;
import com.example.employee.infrastructure.mapper.EmployeeMapper;
import com.example.employee.infrastructure.persistence.data.SpringDataEmployeeRepository;
import com.example.employee.infrastructure.persistence.entity.EmployeeJpaEntity;
import com.example.employee.infrastructure.persistence.entity.Gender;
import com.example.employee.infrastructure.persistence.entity.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeJpaRepositoryAdapterTest {

    @Mock
    private SpringDataEmployeeRepository springDataRepository;

    @Mock
    private EmployeeMapper mapper;

    @InjectMocks
    private EmployeeJpaRepositoryAdapter adapter;

    private Employee employee;
    private EmployeeJpaEntity employeeJpaEntity;

    @BeforeEach
    void setUp() {
        // Configurar Employee de dominio
        employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setMiddleName("Doe");
        employee.setLastName("Lingam");
        employee.setMothersLastName("Smith");
        employee.setBirthDate(LocalDate.of(1990, 1, 1));
        employee.setGender(com.example.employee.domain.model.Gender.MAN);
        employee.setPosition(com.example.employee.domain.model.Position.TESTER);

        // Configurar EmployeeJpaEntity
        employeeJpaEntity = new EmployeeJpaEntity();
        employeeJpaEntity.setId(1L);
        employeeJpaEntity.setFirstName("John");
        employeeJpaEntity.setMiddleName("Doe");
        employeeJpaEntity.setLastName("Lingam");
        employeeJpaEntity.setMothersLastName("Smith");
        employeeJpaEntity.setBirthDate(LocalDate.of(1990, 1, 1));
        employeeJpaEntity.setGender(Gender.MAN);
        employeeJpaEntity.setPosition(Position.TESTER);
        employeeJpaEntity.prePersist();
    }

    @Test
    void save_ShouldSaveAndReturnEmployee() {
        // Arrange
        when(mapper.toJpaEntity(any(Employee.class))).thenReturn(employeeJpaEntity);
        when(springDataRepository.save(any(EmployeeJpaEntity.class))).thenReturn(employeeJpaEntity);
        when(mapper.toDomain(any(EmployeeJpaEntity.class))).thenReturn(employee);

        // Act
        Employee result = adapter.save(employee);

        // Assert
        assertNotNull(result);
        assertEquals(employee.getId(), result.getId());
        assertEquals(employee.getFirstName(), result.getFirstName());
        assertEquals(employee.getMiddleName(), result.getMiddleName());
        assertEquals(employee.getLastName(), result.getLastName());
        assertEquals(employee.getMothersLastName(), result.getMothersLastName());
        assertEquals(employee.getId(), result.getId());
        assertEquals(employee.getId(), result.getId());
        verify(mapper).toJpaEntity(employee);
        verify(springDataRepository).save(employeeJpaEntity);
        verify(mapper).toDomain(employeeJpaEntity);
    }

    @Test
    void save_ShouldSaveAndReturnEmployeeWithNullAge() {
        employee.setBirthDate(null);
        employeeJpaEntity.setBirthDate(null);

        // Arrange
        when(mapper.toJpaEntity(any(Employee.class))).thenReturn(employeeJpaEntity);
        when(springDataRepository.save(any(EmployeeJpaEntity.class))).thenReturn(employeeJpaEntity);
        when(mapper.toDomain(any(EmployeeJpaEntity.class))).thenReturn(employee);

        // Act
        Employee result = adapter.save(employee);

        // Assert
        assertNotNull(result);
        assertEquals(employee.getId(), result.getId());
        assertEquals(employee.getFirstName(), result.getFirstName());
        assertEquals(employee.getMiddleName(), result.getMiddleName());
        assertEquals(employee.getLastName(), result.getLastName());
        assertEquals(employee.getMothersLastName(), result.getMothersLastName());
        assertEquals(employee.getId(), result.getId());
        assertEquals(employee.getId(), result.getId());
        verify(mapper).toJpaEntity(employee);
        verify(springDataRepository).save(employeeJpaEntity);
        verify(mapper).toDomain(employeeJpaEntity);
    }

    @Test
    void saveAll_ShouldSaveAndReturnListOfEmployees() {
        // Arrange
        List<Employee> employees = Collections.singletonList(employee);
        List<EmployeeJpaEntity> jpaEntities = Collections.singletonList(employeeJpaEntity);

        when(mapper.toJpaEntityList(any())).thenReturn(jpaEntities);
        when(springDataRepository.saveAll(any())).thenReturn(jpaEntities);
        when(mapper.toDomainListFromJpa(any())).thenReturn(employees);

        // Act
        List<Employee> result = adapter.saveAll(employees);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(mapper).toJpaEntityList(employees);
        verify(springDataRepository).saveAll(jpaEntities);
        verify(mapper).toDomainListFromJpa(jpaEntities);
    }

    @Test
    void findById_WhenEmployeeExists_ShouldReturnEmployee() {
        // Arrange
        when(springDataRepository.findById(1L)).thenReturn(Optional.of(employeeJpaEntity));
        when(mapper.toDomain(any(EmployeeJpaEntity.class))).thenReturn(employee);

        // Act
        Optional<Employee> result = adapter.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(employee.getId(), result.get().getId());
        verify(springDataRepository).findById(1L);
        verify(mapper).toDomain(employeeJpaEntity);
    }

    @Test
    void findById_WhenEmployeeDoesNotExist_ShouldReturnEmpty() {
        // Arrange
        when(springDataRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<Employee> result = adapter.findById(1L);

        // Assert
        assertTrue(result.isEmpty());
        verify(springDataRepository).findById(1L);
        verify(mapper, never()).toDomain(any(EmployeeJpaEntity.class));
    }

    @Test
    void findAll_ShouldReturnPagedEmployees() {
        // Arrange
        PageRequest pageRequest = new PageRequest(0, 10, "id", "asc");
        List<EmployeeJpaEntity> jpaEntities = Collections.singletonList(employeeJpaEntity);
        PageImpl<EmployeeJpaEntity> page = new PageImpl<>(jpaEntities, Pageable.ofSize(10), 1);

        when(springDataRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(mapper.toDomain(any(EmployeeJpaEntity.class))).thenReturn(employee);

        // Act
        Page<Employee> result = adapter.findAll(pageRequest);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(0, result.getPageNumber());
        assertEquals(10, result.getPageSize());
        verify(springDataRepository).findAll(any(Pageable.class));
        verify(mapper).toDomain(employeeJpaEntity);
    }

    @Test
    void deleteById_WhenEmployeeExists_ShouldReturnTrue() {
        // Arrange
        when(springDataRepository.existsById(1L)).thenReturn(true);

        // Act
        boolean result = adapter.deleteById(1L);

        // Assert
        assertTrue(result);
        verify(springDataRepository).existsById(1L);
        verify(springDataRepository).deleteById(1L);
    }

    @Test
    void deleteById_WhenEmployeeDoesNotExist_ShouldReturnFalse() {
        // Arrange
        when(springDataRepository.existsById(1L)).thenReturn(false);

        // Act
        boolean result = adapter.deleteById(1L);

        // Assert
        assertFalse(result);
        verify(springDataRepository).existsById(1L);
        verify(springDataRepository, never()).deleteById(any());
    }

    @Test
    void existsById_ShouldReturnCorrectBoolean() {
        // Arrange
        when(springDataRepository.existsById(1L)).thenReturn(true);

        // Act
        boolean result = adapter.existsById(1L);

        // Assert
        assertTrue(result);
        verify(springDataRepository).existsById(1L);
    }
} 