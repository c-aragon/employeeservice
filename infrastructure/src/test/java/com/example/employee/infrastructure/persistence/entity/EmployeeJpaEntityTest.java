package com.example.employee.infrastructure.persistence.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class EmployeeJpaEntityTest {

    @Test
    public void prePersistWithAgeTest() {
        EmployeeJpaEntity employeeJpaEntity = new EmployeeJpaEntity();
        employeeJpaEntity.setBirthDate(LocalDate.of(1980, 1, 1));

        employeeJpaEntity.prePersist();

        Assertions.assertEquals(45, employeeJpaEntity.getAge());
    }

    @Test
    public void prePersistWithNullAgeTest() {
        EmployeeJpaEntity employeeJpaEntity = new EmployeeJpaEntity();
        employeeJpaEntity.setBirthDate(null);

        employeeJpaEntity.prePersist();

        Assertions.assertNull(employeeJpaEntity.getAge());
    }

}
