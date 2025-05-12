package com.example.employee.infrastructure.mapper;

import com.example.employee.domain.model.Employee;
import com.example.employee.infrastructure.persistence.entity.EmployeeJpaEntity;
import com.example.employee.infrastructure.persistence.entity.Gender;
import com.example.employee.infrastructure.persistence.entity.Position;
import com.example.employee.infrastructure.web.dto.request.EmployeeRequest;
import com.example.employee.infrastructure.web.dto.request.UpdateEmployeeRequest;
import com.example.employee.infrastructure.web.dto.response.EmployeeResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeMapperImpl implements EmployeeMapper {


    @Override
    public Employee toDomain(EmployeeRequest request) {
        if ( request == null ) {
            return null;
        }

        Employee employee = new Employee();

        employee.setFirstName( request.getFirstName() );
        employee.setMiddleName( request.getMiddleName() );
        employee.setLastName( request.getLastName() );
        employee.setMothersLastName( request.getMothersLastName() );
        if ( request.getGender() != null ) {
            employee.setGender( request.getGender().name() );
        }
        employee.setBirthDate( request.getBirthDate() );
        if ( request.getPosition() != null ) {
            employee.setPosition( request.getPosition().name() );
        }

        return employee;
    }

    @Override
    public EmployeeResponse toResponse(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        EmployeeResponse employeeResponse = new EmployeeResponse();

        employeeResponse.setId( employee.getId() );
        employeeResponse.setFirstName( employee.getFirstName() );
        employeeResponse.setMiddleName( employee.getMiddleName() );
        employeeResponse.setLastName( employee.getLastName() );
        employeeResponse.setMothersLastName( employee.getMothersLastName() );
        employeeResponse.setAge( employee.getAge() );
        employeeResponse.setGender( employee.getGender() );
        employeeResponse.setBirthDate( employee.getBirthDate() );
        employeeResponse.setPosition( employee.getPosition() );

        return employeeResponse;
    }

    @Override
    public EmployeeJpaEntity toJpaEntity(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        EmployeeJpaEntity employeeJpaEntity = new EmployeeJpaEntity();

        employeeJpaEntity.setId( employee.getId() );
        employeeJpaEntity.setFirstName( employee.getFirstName() );
        employeeJpaEntity.setMiddleName( employee.getMiddleName() );
        employeeJpaEntity.setLastName( employee.getLastName() );
        employeeJpaEntity.setMothersLastName( employee.getMothersLastName() );
        employeeJpaEntity.setAge( employee.getAge() );
        if ( employee.getGender() != null ) {
            employeeJpaEntity.setGender( Enum.valueOf( Gender.class, employee.getGender() ) );
        }
        employeeJpaEntity.setBirthDate( employee.getBirthDate() );
        if ( employee.getPosition() != null ) {
            employeeJpaEntity.setPosition( Enum.valueOf( Position.class, employee.getPosition() ) );
        }

        return employeeJpaEntity;
    }

    @Override
    public List<EmployeeJpaEntity> toJpaEntityList(List<Employee> employees) {
        if ( employees == null ) {
            return null;
        }

        List<EmployeeJpaEntity> list = new ArrayList<>( employees.size() );
        for ( Employee employee : employees ) {
            list.add( toJpaEntity( employee ) );
        }

        return list;
    }

    @Override
    public Employee toDomain(EmployeeJpaEntity jpaEntity) {
        if ( jpaEntity == null ) {
            return null;
        }

        Employee employee = new Employee();

        employee.setId( jpaEntity.getId() );
        employee.setFirstName( jpaEntity.getFirstName() );
        employee.setMiddleName( jpaEntity.getMiddleName() );
        employee.setLastName( jpaEntity.getLastName() );
        employee.setMothersLastName( jpaEntity.getMothersLastName() );
        employee.setAge( jpaEntity.getAge() );
        if ( jpaEntity.getGender() != null ) {
            employee.setGender( jpaEntity.getGender().name() );
        }
        employee.setBirthDate( jpaEntity.getBirthDate() );
        if ( jpaEntity.getPosition() != null ) {
            employee.setPosition( jpaEntity.getPosition().name() );
        }

        return employee;
    }

    @Override
    public List<Employee> toDomainListFromJpa(List<EmployeeJpaEntity> jpaEntities) {
        if ( jpaEntities == null ) {
            return null;
        }

        List<Employee> list = new ArrayList<>( jpaEntities.size() );
        for ( EmployeeJpaEntity employeeJpaEntity : jpaEntities ) {
            list.add( toDomain( employeeJpaEntity ) );
        }

        return list;
    }

    @Override
    public void updateDomainFromDto(UpdateEmployeeRequest dto, Employee employee) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getFirstName() != null ) {
            employee.setFirstName( dto.getFirstName() );
        }
        if ( dto.getMiddleName() != null ) {
            employee.setMiddleName( dto.getMiddleName() );
        }
        if ( dto.getLastName() != null ) {
            employee.setLastName( dto.getLastName() );
        }
        if ( dto.getMothersLastName() != null ) {
            employee.setMothersLastName( dto.getMothersLastName() );
        }
        if ( dto.getGender() != null ) {
            employee.setGender( dto.getGender().name() );
        }
        if ( dto.getBirthDate() != null ) {
            employee.setBirthDate( dto.getBirthDate() );
        }
        if ( dto.getPosition() != null ) {
            employee.setPosition( dto.getPosition().name() );
        }
    }

}
