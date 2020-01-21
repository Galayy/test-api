package com.example.testapi.unit;

import com.example.testapi.TestApiApplication;
import com.example.testapi.api.EmployeesApi;
import com.example.testapi.exception.ExceptionHandling;
import com.example.testapi.exception.UnexpectedError;
import com.example.testapi.model.Employee;
import com.example.testapi.service.impl.EmployeeServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static com.example.testapi.utils.TestUtils.*;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class EmployeesApiTest {

    private static final String EMPLOYEE_URL = "/api/v1/employees/";
    private static final String INVALID_AGE_MESSAGE = "Age must be between 18 and 70.";

    @MockBean
    private EmployeesApi employeesApi;
    @MockBean
    private EmployeeServiceImpl employeeService;

    private ObjectMapper objectMapper;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.objectMapper = new ObjectMapper();
        this.mockMvc = MockMvcBuilders.standaloneSetup(employeesApi)
                .setControllerAdvice(new ExceptionHandling()).build();
        this.employeesApi = new EmployeesApi(employeeService);
    }

    @Test
    public void createHappyPath() throws Exception {
        //GIVEN
        var input = createEmployeeInput();

        //WHEN
        when(employeeService.create(input)).thenReturn(new Employee());

        var result = mockMvc.perform(post(EMPLOYEE_URL)
                .content(objectMapper.writeValueAsString(input)).contentType(APPLICATION_JSON));

        //THEN
        result.andExpect(status().isOk());
    }

    @Test
    public void createWithInvalidAge() throws Exception {
        //GIVEN
        var input = createInvalidEmployeeInput();

        //WHEN
        var mvcResult = mockMvc.perform(post(EMPLOYEE_URL)
                .content(objectMapper.writeValueAsString(input)).contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn().getResponse();
        final List<UnexpectedError> errors = objectMapper.readValue(mvcResult.getContentAsByteArray(),
                new TypeReference<>() {
                });

        //THEN
        assertSoftly(softly -> {
            softly.assertThat(errors.size()).isEqualTo(1);
            softly.assertThat(errors.get(0).getMessage()).isEqualTo(INVALID_AGE_MESSAGE);
        });
    }

    @Test
    public void updateHappyPath() throws Exception {
        //GIVEN
        var input = createEmployeeInput();

        //WHEN
        when(employeeService.update(UUID.randomUUID(), input)).thenReturn(new Employee());

        var result = mockMvc.perform(put(EMPLOYEE_URL + UUID.randomUUID())
                .content(objectMapper.writeValueAsString(input)).contentType(APPLICATION_JSON));

        //THEN
        result.andExpect(status().isOk());
    }

    private static final Faker FAKE = Faker.instance(Locale.US, ThreadLocalRandom.current());

    @Test
    public void updateWithInvalidAge() throws Exception {
        //GIVEN
        var input = createInvalidEmployeeInput();

        //WHEN
        var mvcResult = mockMvc.perform(put(EMPLOYEE_URL + FAKE.internet().uuid())
                .content(objectMapper.writeValueAsString(input)).contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn().getResponse();
        final List<UnexpectedError> errors = objectMapper.readValue(mvcResult.getContentAsByteArray(),
                new TypeReference<>() {
                });

        //THEN
        assertSoftly(softly -> {
            softly.assertThat(errors.size()).isEqualTo(1);
            softly.assertThat(errors.get(0).getMessage()).isEqualTo(INVALID_AGE_MESSAGE);
        });
    }

}
