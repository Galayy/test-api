package com.example.testapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class InvalidInputDataException extends RuntimeException {

    @Getter
    private HttpStatus status;

    public InvalidInputDataException(String message) {
        super(message);
        this.status = BAD_REQUEST;
    }

}
