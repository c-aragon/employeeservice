package com.example.employee.infrastructure.web.dto.request;

import com.example.employee.infrastructure.persistence.entity.Gender;
import com.example.employee.infrastructure.persistence.entity.Position;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest {

    @NotBlank(message = "El primer nombre no puede estar vacío")
    @Size(max = 50, message = "El primer nombre no puede exceder los 50 caracteres")
    private String firstName;

    @Size(max = 50, message = "El segundo nombre no puede exceder los 50 caracteres")
    private String middleName;

    @NotBlank(message = "El apellido paterno no puede estar vacío")
    @Size(max = 50, message = "El apellido paterno no puede exceder los 50 caracteres")
    private String lastName;

    @Size(max = 50, message = "El apellido materno no puede exceder los 50 caracteres")
    private String mothersLastName;

    private Gender gender;

    @NotNull
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthDate;

    private Position position;

}
