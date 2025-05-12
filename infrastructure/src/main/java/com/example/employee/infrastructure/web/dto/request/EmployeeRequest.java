package com.example.employee.infrastructure.web.dto.request;

import com.example.employee.domain.model.Gender;
import com.example.employee.domain.model.Position;
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

    @NotBlank(message = "First name can't be empty")
    @Size(max = 50, message = "First name cannot exceed 50 characters")
    private String firstName;

    @Size(max = 50, message = "Middle name cannot exceed 50 characters")
    private String middleName;

    @NotBlank(message = "Last name can't be empty")
    @Size(max = 50, message = "Last name cannot exceed 50 characters")
    private String lastName;

    @Size(max = 50, message = "Mother last name cannot exceed 50 characters")
    private String mothersLastName;

    private Gender gender;

    @NotNull(message = "Birth date can't be null")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthDate;

    private Position position;

}
