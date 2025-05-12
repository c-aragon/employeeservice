package com.example.employee.domain.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Employee {

    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String mothersLastName;
    private Integer age;
    private String gender;
    private LocalDate birthDate;
    private String position;

}
