package com.example.employee.infrastructure.web.dto.response;

import com.example.employee.domain.model.Gender;
import com.example.employee.domain.model.Position;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {

    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String mothersLastName;
    private Integer age;
    private Gender gender;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthDate;
    private Position position;

}
