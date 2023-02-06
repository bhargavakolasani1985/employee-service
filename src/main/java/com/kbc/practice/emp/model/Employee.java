package com.kbc.practice.emp.model;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Employee {
    private long id;
    private String name;
    private String role;
    private long salary;
    private String deptId;

}
