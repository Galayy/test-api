package com.example.testapi.api;

import com.example.testapi.model.EmployeeInput;
import com.example.testapi.model.Employee;
import com.example.testapi.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employees")
public class EmployeesApi {

    private final EmployeeService employeeService;

    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAll() {
        var employees = employeeService.getAll();
        return new ResponseEntity<>(employees, OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable UUID id) {
        var employee = employeeService.getById(id);
        return new ResponseEntity<>(employee, OK);
    }

    @PostMapping
    public ResponseEntity<Employee> create(@Valid EmployeeInput input) {
        var employee = employeeService.create(input);
        return new ResponseEntity<>(employee, CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> create(@PathVariable UUID id, @Valid EmployeeInput input) {
        var employee = employeeService.update(id, input);
        return new ResponseEntity<>(employee, OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> create(@PathVariable UUID id) {
        employeeService.delete(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

}
