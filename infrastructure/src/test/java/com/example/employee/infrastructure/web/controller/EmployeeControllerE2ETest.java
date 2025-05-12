package com.example.employee.infrastructure.web.controller;

import com.example.employee.infrastructure.persistence.entity.Position;
import com.example.employee.infrastructure.web.dto.request.EmployeeRequest;
import com.example.employee.infrastructure.web.dto.request.UpdateEmployeeRequest;
import com.example.employee.infrastructure.web.dto.response.EmployeeResponse;
import com.example.employee.infrastructure.web.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EmployeeControllerE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateAndGetEmployee() throws Exception {
        // Create employee
        EmployeeRequest request = new EmployeeRequest();
        request.setFirstName("SMITH");
        request.setLastName("SANDERS");
        request.setBirthDate(LocalDate.of(1990, 1, 1));
        request.setPosition(com.example.employee.domain.model.Position.DEVELOPER);

        MvcResult result = mockMvc.perform(post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(List.of(request))))
                .andExpect(status().isCreated())
                .andReturn();

        List<EmployeeResponse> response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, EmployeeResponse.class)
        );

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals("SMITH", response.getFirst().getFirstName());
        assertEquals("SANDERS", response.getFirst().getLastName());

        // Get employee
        mockMvc.perform(get("/api/v1/employees")
                .param("page", "0")
                .param("size", "10")
                .param("sortBy", "id")
                .param("direction", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].firstName").value("SMITH"))
                .andExpect(jsonPath("$.content[0].lastName").value("SANDERS"));
    }

    @Test
    void testCreateWithNullFirstName() throws Exception {
        // Create employee
        EmployeeRequest request = new EmployeeRequest();
        request.setFirstName(null);
        request.setLastName("SANDERS");
        request.setBirthDate(LocalDate.of(1990, 1, 1));
        request.setPosition(com.example.employee.domain.model.Position.DEVELOPER);

        MvcResult result = mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(request))))
                .andExpect(status().isBadRequest())
                .andReturn();

        ErrorResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);

        assertNotNull(response);
        assertTrue(response.getMessage().startsWith("Validation failure"));
    }

    @Test
    void testCreateEmptyEmployee() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ArrayList<EmployeeRequest>())))
                .andExpect(status().isBadRequest())
                .andReturn();

        ErrorResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);

        assertNotNull(response);
        assertEquals("Employee requests are empty", response.getMessage());
    }

    @Test
    void testUpdateEmployee() throws Exception {
        // Create employee
        EmployeeRequest createRequest = new EmployeeRequest();
        createRequest.setFirstName("BESHI");
        createRequest.setLastName("COLLEMAN");
        createRequest.setMiddleName("SMITH");
        createRequest.setMothersLastName("SANDERS");
        createRequest.setBirthDate(LocalDate.of(1992, 5, 15));
        createRequest.setPosition(com.example.employee.domain.model.Position.TESTER);

        MvcResult createResult = mockMvc.perform(post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(List.of(createRequest))))
                .andExpect(status().isCreated())
                .andReturn();

        List<EmployeeResponse> createResponse = objectMapper.readValue(
                createResult.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, EmployeeResponse.class)
        );

        Long employeeId = createResponse.getFirst().getId();

        // Update employee
        UpdateEmployeeRequest updateRequest = new UpdateEmployeeRequest();
        updateRequest.setFirstName("BESHI");
        updateRequest.setPosition(com.example.employee.domain.model.Position.CTO);

        mockMvc.perform(put("/api/v1/employees/" + employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("BESHI"))
                .andExpect(jsonPath("$.lastName").value("COLLEMAN"))
                .andExpect(jsonPath("$.mothersLastName").value("SANDERS"))
                .andExpect(jsonPath("$.middleName").value("SMITH"))
                .andExpect(jsonPath("$.position").value(Position.CTO.toString()));
    }

    @Test
    void testDeleteEmployee() throws Exception {
        // Create employee
        EmployeeRequest request = new EmployeeRequest();
        request.setFirstName("Carlos");
        request.setLastName("López");
        request.setBirthDate(LocalDate.of(1988, 8, 20));
        request.setPosition(com.example.employee.domain.model.Position.ARCHITECT);

        MvcResult createResult = mockMvc.perform(post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(List.of(request))))
                .andExpect(status().isCreated())
                .andReturn();

        List<EmployeeResponse> createResponse = objectMapper.readValue(
                createResult.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, EmployeeResponse.class)
        );

        Long employeeId = createResponse.getFirst().getId();

        // Delete employee
        mockMvc.perform(delete("/api/v1/employees/" + employeeId))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteUnexistsEmployee() throws Exception {
        MvcResult result = mockMvc.perform(delete("/api/v1/employees/100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        ErrorResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);

        assertNotNull(response);
        assertEquals("Employee with id 100 not found", response.getMessage());
    }

    @Test
    void testCreateMultipleEmployees() throws Exception {
        List<EmployeeRequest> requests = Arrays.asList(
                createEmployeeRequest("Ana", "Martínez",
                        LocalDate.of(1995, 3, 10), com.example.employee.domain.model.Position.CTO),
                createEmployeeRequest("Pedro", "Sánchez",
                        LocalDate.of(1991, 7, 22), com.example.employee.domain.model.Position.ARCHITECT),
                createEmployeeRequest("Laura", "Rodríguez",
                        LocalDate.of(1993, 11, 5), com.example.employee.domain.model.Position.TESTER)
        );

        MvcResult result = mockMvc.perform(post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requests)))
                .andExpect(status().isCreated())
                .andReturn();

        List<EmployeeResponse> response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, EmployeeResponse.class)
        );

        assertEquals(3, response.size());
        assertTrue(response.stream().anyMatch(e -> e.getFirstName().equals("Ana")));
        assertTrue(response.stream().anyMatch(e -> e.getFirstName().equals("Pedro")));
        assertTrue(response.stream().anyMatch(e -> e.getFirstName().equals("Laura")));
    }

    private EmployeeRequest createEmployeeRequest(String firstName, String lastName, LocalDate birthDate,
                                                  com.example.employee.domain.model.Position position) {
        EmployeeRequest request = new EmployeeRequest();
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setBirthDate(birthDate);
        request.setPosition(position);
        return request;
    }
} 