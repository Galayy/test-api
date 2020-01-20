package com.example.testapi.integration;

import com.example.testapi.initializers.PostgresInitializer;
import com.example.testapi.model.Employee;
import com.example.testapi.repository.EmployeeRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static com.example.testapi.utils.TestUtils.createEmployeeEntity;
import static com.example.testapi.utils.TestUtils.createEmployeeInput;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = {
        PostgresInitializer.class
})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeesApiTest {

    private static final String EMPLOYEE_URL = "/api/v1/employees/";

    @Autowired
    protected EmployeeRepository employeeRepository;

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;

    @AfterEach
    public void clearDatabase() {
        employeeRepository.deleteAll();
    }

    @Test
    public void create_happyPath() throws Exception {
        //GIVEN
        var input = createEmployeeInput();

        //WHEN
        var mvcResult = mockMvc.perform(post(EMPLOYEE_URL)
                .content(objectMapper.writeValueAsString(input)).contentType(APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn().getResponse();
        var employee = objectMapper.readValue(mvcResult.getContentAsByteArray(), Employee.class);

        //THEN
        assertSoftly(softly -> {
            softly.assertThat(input.getAge()).isEqualTo(employee.getAge());
            softly.assertThat(input.getName()).isEqualTo(employee.getName());
            softly.assertThat(employee.getId()).isNotNull();
        });
    }

    @Test
    public void update_happyPath() throws Exception {
        //GIVEN
        var entity = employeeRepository.save(createEmployeeEntity());
        var input = createEmployeeInput();

        //WHEN
        var mvcResult = mockMvc.perform(put(EMPLOYEE_URL + entity.getId())
                .content(objectMapper.writeValueAsString(input)).contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse();
        var updatedEmployee = objectMapper.readValue(mvcResult.getContentAsByteArray(), Employee.class);

        //THEN
        assertSoftly(softly -> {
            softly.assertThat(input.getAge()).isEqualTo(updatedEmployee.getAge());
            softly.assertThat(input.getName()).isEqualTo(updatedEmployee.getName());
            softly.assertThat(entity.getId()).isEqualTo(updatedEmployee.getId());
        });
    }

    @Test
    public void updateWhenNotFound() throws Exception {
        //GIVEN
        var input = createEmployeeInput();

        //WHEN
        var mvcResult = mockMvc.perform(put(EMPLOYEE_URL + UUID.randomUUID())
                .content(objectMapper.writeValueAsString(input)).contentType(APPLICATION_JSON));

        //THEN
        mvcResult.andExpect(status().isNotFound());
    }

    @Test
    public void delete_happyPath() throws Exception {
        //GIVEN
        var entity = employeeRepository.save(createEmployeeEntity());

        //WHEN
        mockMvc.perform(delete(EMPLOYEE_URL + entity.getId())).andExpect(status().isNoContent());

        //THEN
        assertThat(employeeRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    public void getAll_happyPath() throws Exception {
        //GIVEN
        employeeRepository.save(createEmployeeEntity());
        employeeRepository.save(createEmployeeEntity());

        //WHEN
        var mvcResult = mockMvc.perform(get(EMPLOYEE_URL + "all")).andExpect(status().isOk())
                .andReturn().getResponse();
        final List<Employee> employees = objectMapper.readValue(mvcResult.getContentAsByteArray(),
                new TypeReference<>() {
                });

        //THEN
        assertThat(employees.size()).isEqualTo(2);
    }

    @Test
    public void getById_happyPath() throws Exception {
        //GIVEN
        var entity = employeeRepository.save(createEmployeeEntity());

        //WHEN
        var mvcResult = mockMvc.perform(get(EMPLOYEE_URL + entity.getId())).andExpect(status().isOk())
                .andReturn().getResponse();
        var employee = objectMapper.readValue(mvcResult.getContentAsByteArray(), Employee.class);

        //THEN
        assertThat(entity.getId()).isEqualTo(employee.getId());
    }

    @Test
    public void getByIdWhenNotFound() throws Exception {
        //GIVEN
        //WHEN
        var mvcResult = mockMvc.perform(get(EMPLOYEE_URL + UUID.randomUUID()));
        //THEN
        mvcResult.andExpect(status().isNotFound());
    }

}
