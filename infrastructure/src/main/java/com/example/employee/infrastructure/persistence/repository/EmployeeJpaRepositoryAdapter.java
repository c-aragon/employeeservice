package com.example.employee.infrastructure.persistence.repository;

import com.example.employee.application.port.out.EmployeeRepositoryPort;
import com.example.employee.domain.model.Employee;
import com.example.employee.domain.model.Page;
import com.example.employee.domain.model.PageRequest;
import com.example.employee.infrastructure.mapper.EmployeeMapper;
import com.example.employee.infrastructure.persistence.data.SpringDataEmployeeRepository;
import com.example.employee.infrastructure.persistence.entity.EmployeeJpaEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class EmployeeJpaRepositoryAdapter implements EmployeeRepositoryPort {

    private final SpringDataEmployeeRepository springDataRepository;
    private final EmployeeMapper mapper;

    public EmployeeJpaRepositoryAdapter(SpringDataEmployeeRepository springDataRepository, EmployeeMapper mapper) {
        this.springDataRepository = springDataRepository;
        this.mapper = mapper;
    }


    @Override
    @Transactional
    public Employee save(Employee employee) {
        EmployeeJpaEntity jpaEntity = mapper.toJpaEntity(employee);
        EmployeeJpaEntity savedEntity = springDataRepository.save(jpaEntity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    @Transactional
    public List<Employee> saveAll(List<Employee> employees) {
        List<EmployeeJpaEntity> jpaEntities = mapper.toJpaEntityList(employees);
        List<EmployeeJpaEntity> savedEntities = springDataRepository.saveAll(jpaEntities);
        return mapper.toDomainListFromJpa(savedEntities);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Employee> findById(Long id) {
        return springDataRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Employee> findAll(PageRequest pageRequest) {
        Sort.Direction direction = Sort.Direction.fromString(pageRequest.getDirection().toUpperCase());
        Pageable pageable = org.springframework.data.domain.PageRequest.of(
                pageRequest.getPageNumber(),
                pageRequest.getPageSize(),
                Sort.by(direction, pageRequest.getSortBy())
        );

        org.springframework.data.domain.Page<EmployeeJpaEntity> page =
                springDataRepository.findAll(pageable);

        return new Page<>(
                page.getContent().stream().map(mapper::toDomain).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        if (springDataRepository.existsById(id)) {
            springDataRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return springDataRepository.existsById(id);
    }
}
