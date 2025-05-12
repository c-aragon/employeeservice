package com.example.employee.infrastructure.persistence.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "employee")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "middle_name", length = 50)
    private String middleName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "mothers_last_name", length = 50)
    private String mothersLastName;

    @Column(name = "age")
    private Integer age;

    @Enumerated(EnumType.ORDINAL)
    private Gender gender;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "position")
    private Position position;

    @PrePersist
    public void prePersist() {
        calculateAge();
    }

    @PreUpdate
    private void preUpdate() {
        calculateAge();
    }

    private void calculateAge() {
        if (this.birthDate != null) {
            this.age = Period.between(this.birthDate, LocalDate.now()).getYears();
        } else {
            this.age = null;
        }
    }
}
