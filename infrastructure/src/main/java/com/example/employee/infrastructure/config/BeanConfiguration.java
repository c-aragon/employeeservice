package com.example.employee.infrastructure.config;

import com.example.employee.application.port.out.EmployeeRepositoryPort;
import com.example.employee.application.port.service.EmployeeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public EmployeeService employeeService(EmployeeRepositoryPort employeeRepositoryPort) {
        return new EmployeeService(employeeRepositoryPort);
    }

}
