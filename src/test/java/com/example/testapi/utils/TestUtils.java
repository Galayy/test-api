package com.example.testapi.utils;

import com.example.testapi.entity.EmployeeEntity;
import com.example.testapi.model.EmployeeInput;
import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;

import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@UtilityClass
public class TestUtils {

    private static final Faker FAKE = Faker.instance(Locale.US, ThreadLocalRandom.current());

    public static EmployeeInput createEmployeeInput() {
        return EmployeeInput.builder()
                .age(FAKE.random().nextInt(18, 70))
                .name(FAKE.name().fullName())
                .position(FAKE.company().profession())
                .salary(FAKE.commerce().price().hashCode())
                .build();
    }

    public static EmployeeEntity createEmployeeEntity() {
        return EmployeeEntity.builder()
                .id(UUID.randomUUID())
                .age(FAKE.random().nextInt(18, 70))
                .name(FAKE.name().fullName())
                .position(FAKE.company().profession())
                .salary(FAKE.commerce().price().hashCode())
                .build();
    }

}
