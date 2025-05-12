package com.example.employee.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageRequest {

    private int pageNumber;
    private int pageSize;
    private String sortBy;
    private String direction;

}
