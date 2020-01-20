package com.example.testapi.service.impl;

import com.example.testapi.entity.EmployeeEntity;
import com.example.testapi.exception.EntityNotFoundException;
import com.example.testapi.model.Employee;
import com.example.testapi.model.EmployeeInput;
import com.example.testapi.repository.EmployeeRepository;
import com.example.testapi.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.testapi.mapper.EmployeeMapper.EMPLOYEE_MAPPER;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Employee> getAll() {
        return employeeRepository.findAll().stream().map(EMPLOYEE_MAPPER::toModel).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Employee getById(UUID id) {
        var entity = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("Employee with id %s not found", id)));
        return EMPLOYEE_MAPPER.toModel(entity);
    }

    @Override
    @Transactional
    public Employee create(EmployeeInput input) {
        var entity = EmployeeEntity.builder()
                .age(input.getAge())
                .name(input.getName())
                .position(input.getPosition())
                .salary(input.getSalary())
                .build();
        var savedEntity = employeeRepository.save(entity);
        return EMPLOYEE_MAPPER.toModel(savedEntity);
    }

    @Override
    @Transactional
    public Employee update(UUID id, EmployeeInput input) {
        var entity = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("Employee with id %s not found", id)));
        entity.setAge(input.getAge());
        entity.setName(input.getName());
        entity.setPosition(input.getPosition());
        entity.setSalary(input.getSalary());
        var updatedEntity = employeeRepository.save(entity);
        return EMPLOYEE_MAPPER.toModel(updatedEntity);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        employeeRepository.deleteById(id);
    }
}
