package com.example.employee.infrastructure.web.dto.request;

import com.example.employee.domain.model.Gender;
import com.example.employee.domain.model.Position;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEmployeeRequest {

    @Size(max = 50, message = "El primer nombre no puede exceder los 50 caracteres")
    private String firstName;

    @Size(max = 50, message = "El segundo nombre no puede exceder los 50 caracteres")
    private String middleName;

    @Size(max = 50, message = "El apellido paterno no puede exceder los 50 caracteres")
    private String lastName;

    @Size(max = 50, message = "El apellido materno no puede exceder los 50 caracteres")
    private String mothersLastName;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthDate;

    private Gender gender;

    private Position position;

}
