package com.example.testapi.model;

import com.example.testapi.validation.annotations.Age;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeInput {

    @Age
    private int age;
    private String name;
    private String position;
    private int salary;

}
