package com.example.testapi.mapper;

import com.example.testapi.entity.EmployeeEntity;
import com.example.testapi.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {

    EmployeeMapper EMPLOYEE_MAPPER = Mappers.getMapper(EmployeeMapper.class);

    Employee toModel(EmployeeEntity userEntity);

}
