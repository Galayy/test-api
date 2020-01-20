package com.example.testapi.service;

import com.example.testapi.model.Employee;
import com.example.testapi.model.EmployeeInput;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {

    List<Employee> getAll();

    Employee getById(UUID id);

    Employee create(EmployeeInput input);

    Employee update(UUID id, EmployeeInput input);

    void delete(UUID id);

}
