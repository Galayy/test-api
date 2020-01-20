package com.example.testapi.validation;

import com.example.testapi.validation.annotations.Age;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AgeValidator implements ConstraintValidator<Age, Integer> {

    private static final int MAX = 70;
    private static final int MIN = 18;

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value >= MIN && value <= MAX;
    }

}
