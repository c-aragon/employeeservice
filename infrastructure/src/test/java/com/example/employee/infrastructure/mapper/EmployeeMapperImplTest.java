package com.example.employee.infrastructure.mapper;

import com.example.employee.domain.model.Employee;
import com.example.employee.infrastructure.persistence.entity.EmployeeJpaEntity;
import com.example.employee.infrastructure.persistence.entity.Gender;
import com.example.employee.infrastructure.persistence.entity.Position;
import com.example.employee.infrastructure.web.dto.request.EmployeeRequest;
import com.example.employee.infrastructure.web.dto.request.UpdateEmployeeRequest;
import com.example.employee.infrastructure.web.dto.response.EmployeeResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class EmployeeMapperImplTest {

    private final EmployeeMapper employeeMapper = new EmployeeMapperImpl();

    private EmployeeJpaEntity employeeJpaEntity = getEmployeeJpaEntity();

    private Employee employee = getEmployee();

    private EmployeeRequest employeeRequest = getEmployeeRequest();

    private UpdateEmployeeRequest updateEmployeeRequest = getUpdateEmployeeRequest();

    @BeforeEach
    void setUp() {
        employeeJpaEntity = getEmployeeJpaEntity();
        employee = getEmployee();
        employeeRequest = getEmployeeRequest();
        updateEmployeeRequest = getUpdateEmployeeRequest();
    }

    @Test
    void toDomainListFromJpaTest() {
        List<Employee> result = employeeMapper.toDomainListFromJpa(List.of(employeeJpaEntity));

        Assertions.assertNotNull(result);
        assertEquals(employeeJpaEntity.getId(), result.getFirst().getId());
        assertEquals(employeeJpaEntity.getFirstName(), result.getFirst().getFirstName());
        assertEquals(employeeJpaEntity.getMiddleName(), result.getFirst().getMiddleName());
        assertEquals(employeeJpaEntity.getLastName(), result.getFirst().getLastName());
        assertEquals(employeeJpaEntity.getMothersLastName(), result.getFirst().getMothersLastName());
        assertEquals(employeeJpaEntity.getAge(), result.getFirst().getAge());
        assertEquals(employeeJpaEntity.getGender().toString(), result.getFirst().getGender());
        assertEquals(employeeJpaEntity.getPosition().toString(), result.getFirst().getPosition());
    }

    @Test
    void toDomainListFromJpaWithNullTest() {
        List<Employee> result = employeeMapper.toDomainListFromJpa(null);

        Assertions.assertNull(result);
    }

    @Test
    void toJpaEntityTest() {
        EmployeeJpaEntity result = employeeMapper.toJpaEntity(employee);

        Assertions.assertNotNull(result);
        assertEquals(employee.getId(), result.getId());
        assertEquals(employee.getFirstName(), result.getFirstName());
        assertEquals(employee.getMiddleName(), result.getMiddleName());
        assertEquals(employee.getLastName(), result.getLastName());
        assertEquals(employee.getMothersLastName(), result.getMothersLastName());
        assertEquals(employee.getAge(), result.getAge());
        assertEquals(employee.getGender(), result.getGender().toString());
        assertEquals(employee.getPosition(), result.getPosition().toString());
    }

    @Test
    void toJpaEntityWithNullTest() {
        EmployeeJpaEntity result = employeeMapper.toJpaEntity(null);

        Assertions.assertNull(result);
    }

    @Test
    void toJpaEntityWithNullPositionTest() {
        employee.setPosition(null);
        EmployeeJpaEntity result = employeeMapper.toJpaEntity(employee);

        Assertions.assertNotNull(result);
        assertEquals(employee.getId(), result.getId());
        assertEquals(employee.getFirstName(), result.getFirstName());
        assertEquals(employee.getMiddleName(), result.getMiddleName());
        assertEquals(employee.getLastName(), result.getLastName());
        assertEquals(employee.getMothersLastName(), result.getMothersLastName());
        assertEquals(employee.getAge(), result.getAge());
        assertEquals(employee.getGender(), result.getGender().toString());
        Assertions.assertNull(result.getPosition());
    }

    @Test
    void toDomainTest() {
        Employee result = employeeMapper.toDomain(employeeRequest);

        Assertions.assertNotNull(result);
        assertEquals(employeeRequest.getFirstName(), result.getFirstName());
        assertEquals(employeeRequest.getMiddleName(), result.getMiddleName());
        assertEquals(employeeRequest.getLastName(), result.getLastName());
        assertEquals(employeeRequest.getMothersLastName(), result.getMothersLastName());
        assertEquals(employeeRequest.getGender().toString(), result.getGender());
        assertEquals(employeeRequest.getPosition().toString(), result.getPosition());
    }

    @Test
    void toDomainWithNullPositionTest() {
        employeeRequest.setPosition(null);
        Employee result = employeeMapper.toDomain(employeeRequest);

        Assertions.assertNotNull(result);
        assertEquals(employeeRequest.getFirstName(), result.getFirstName());
        assertEquals(employeeRequest.getMiddleName(), result.getMiddleName());
        assertEquals(employeeRequest.getLastName(), result.getLastName());
        assertEquals(employeeRequest.getMothersLastName(), result.getMothersLastName());
        assertEquals(employeeRequest.getGender().toString(), result.getGender());
        Assertions.assertNull(employeeRequest.getPosition());
    }

    @Test
    void toDomainWithNullTest() {
        Employee result = employeeMapper.toDomain((EmployeeRequest) null);
        Assertions.assertNull(result);
    }

    @Test
    void toDomainWithNullFromEmployeeJpaEntityTest() {
        Employee result = employeeMapper.toDomain((EmployeeJpaEntity) null);
        Assertions.assertNull(result);
    }

    @Test
    void toDomainFromEmployeeJpaEntityTest() {
        employeeJpaEntity.setPosition(null);
        Employee result = employeeMapper.toDomain(employeeJpaEntity);

        Assertions.assertNotNull(result);
        assertEquals(employeeJpaEntity.getFirstName(), result.getFirstName());
        assertEquals(employeeJpaEntity.getMiddleName(), result.getMiddleName());
        assertEquals(employeeJpaEntity.getLastName(), result.getLastName());
        assertEquals(employeeJpaEntity.getMothersLastName(), result.getMothersLastName());
        assertEquals(employeeJpaEntity.getGender().toString(), result.getGender());
        Assertions.assertNull(result.getPosition());
    }

    @Test
    void toResponseTest() {
        EmployeeResponse result = employeeMapper.toResponse(employee);

        Assertions.assertNotNull(result);
        assertEquals(employee.getId(), result.getId());
        assertEquals(employee.getFirstName(), result.getFirstName());
        assertEquals(employee.getMiddleName(), result.getMiddleName());
        assertEquals(employee.getLastName(), result.getLastName());
        assertEquals(employee.getMothersLastName(), result.getMothersLastName());
        assertEquals(employee.getAge(), result.getAge());
        assertEquals(employee.getGender(), result.getGender());
        assertEquals(employee.getPosition(), result.getPosition());
    }

    @Test
    void toResponseWithNullTest() {
        EmployeeResponse result = employeeMapper.toResponse(null);

        Assertions.assertNull(result);
    }

    @Test
    void updateDomainFromDtoTest() {
        Employee result = new Employee();
        employeeMapper.updateDomainFromDto(updateEmployeeRequest, result);

        Assertions.assertNotNull(result);
        assertEquals(updateEmployeeRequest.getFirstName(), result.getFirstName());
        assertEquals(updateEmployeeRequest.getMiddleName(), result.getMiddleName());
        assertEquals(updateEmployeeRequest.getLastName(), result.getLastName());
        assertEquals(updateEmployeeRequest.getMothersLastName(), result.getMothersLastName());
        assertEquals(updateEmployeeRequest.getGender().toString(), result.getGender());
        assertEquals(updateEmployeeRequest.getPosition().toString(), result.getPosition());
    }

    @Test
    void updateDomainFromDtoWithNullFirstNameTest() {
        Employee result = new Employee();
        updateEmployeeRequest.setFirstName(null);

        employeeMapper.updateDomainFromDto(updateEmployeeRequest, result);

        Assertions.assertNotNull(result);
        Assertions.assertNull(result.getFirstName());
    }

    @Test
    void updateDomainFromDtoWithNullPositionTest() {
        Employee result = new Employee();
        updateEmployeeRequest.setPosition(null);

        employeeMapper.updateDomainFromDto(updateEmployeeRequest, result);

        Assertions.assertNotNull(result);
        Assertions.assertNull(result.getPosition());
    }

    @Test
    void updateDomainFromDtoWithNullTest() {
        Employee employee = new Employee();
        int initHashCode = employee.hashCode();
        employeeMapper.updateDomainFromDto(null, employee);

        Assertions.assertEquals(initHashCode, employee.hashCode());
    }

    @Test
    void toJpaEntityListTest() {
        List<Employee> employeeList = null;

        List<EmployeeJpaEntity>  result = employeeMapper.toJpaEntityList(employeeList);

        Assertions.assertNull(result);
    }

    private EmployeeJpaEntity getEmployeeJpaEntity() {
        EmployeeJpaEntity employeeJpaEntity = new EmployeeJpaEntity();
        employeeJpaEntity.setId(1L);
        employeeJpaEntity.setFirstName("John");
        employeeJpaEntity.setMiddleName("Doe");
        employeeJpaEntity.setLastName("Lingam");
        employeeJpaEntity.setMothersLastName("Smith");
        employeeJpaEntity.setBirthDate(LocalDate.of(1990, 1, 1));
        employeeJpaEntity.setGender(Gender.MAN);
        employeeJpaEntity.setPosition(Position.DEVELOPER);
        return employeeJpaEntity;
    }

    private Employee getEmployee() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setMiddleName("Doe");
        employee.setLastName("Lingam");
        employee.setMothersLastName("Smith");
        employee.setBirthDate(LocalDate.of(1990, 1, 1));
        employee.setGender(Gender.MAN.toString());
        employee.setPosition(Position.DEVELOPER.toString());
        return employee;
    }

    private EmployeeRequest getEmployeeRequest() {
        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setFirstName("John");
        employeeRequest.setMiddleName("Doe");
        employeeRequest.setLastName("Lingam");
        employeeRequest.setMothersLastName("Smith");
        employeeRequest.setBirthDate(LocalDate.of(1990, 1, 1));
        employeeRequest.setGender(Gender.MAN);
        employeeRequest.setPosition(Position.DEVELOPER);
        return employeeRequest;
    }

    private UpdateEmployeeRequest getUpdateEmployeeRequest() {
        UpdateEmployeeRequest updateEmployeeRequest = new UpdateEmployeeRequest();
        updateEmployeeRequest.setFirstName("John");
        updateEmployeeRequest.setMiddleName("Doe");
        updateEmployeeRequest.setLastName("Lingam");
        updateEmployeeRequest.setMothersLastName("Smith");
        updateEmployeeRequest.setBirthDate(LocalDate.of(1990, 1, 1));
        updateEmployeeRequest.setGender(Gender.MAN);
        updateEmployeeRequest.setPosition(Position.DEVELOPER);
        return updateEmployeeRequest;
    }
}
