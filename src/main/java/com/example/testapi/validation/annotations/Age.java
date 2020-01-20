package com.example.testapi.validation.annotations;

import com.example.testapi.validation.AgeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = AgeValidator.class)
public @interface Age {

    String message() default "Age must be between 18 and 70.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
