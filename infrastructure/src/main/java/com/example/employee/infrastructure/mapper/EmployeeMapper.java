package com.example.employee.infrastructure.mapper;

import com.example.employee.domain.model.Employee;
import com.example.employee.infrastructure.persistence.entity.EmployeeJpaEntity;
import com.example.employee.infrastructure.web.dto.request.EmployeeRequest;
import com.example.employee.infrastructure.web.dto.request.UpdateEmployeeRequest;
import com.example.employee.infrastructure.web.dto.response.EmployeeResponse;

import java.util.List;

public interface EmployeeMapper {

    // DTO -> Domain
    Employee toDomain(EmployeeRequest request);

    // Domain -> Response DTO
    EmployeeResponse toResponse(Employee employee);

    // Domain -> JPA Entity
    EmployeeJpaEntity toJpaEntity(Employee employee);
    List<EmployeeJpaEntity> toJpaEntityList(List<Employee> employees);

    // JPA Entity -> Domain
    Employee toDomain(EmployeeJpaEntity jpaEntity);
    List<Employee> toDomainListFromJpa(List<EmployeeJpaEntity> jpaEntities);

    void updateDomainFromDto(UpdateEmployeeRequest dto, Employee employee);

}
