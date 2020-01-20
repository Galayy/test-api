package com.example.testapi.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandling extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleNotFoundException(EntityNotFoundException e, WebRequest request) {
        var ex = new UnexpectedError();
        ex.setMessage(e.getMessage());
        return handleExceptionInternal(e, ex.getMessage(), new HttpHeaders(), e.getStatus(), request);
    }

    @ExceptionHandler(InvalidInputDataException.class)
    protected ResponseEntity<Object> handleIncorrectDataException(InvalidInputDataException e, WebRequest request) {
        var ex = new UnexpectedError();
        ex.setMessage(e.getMessage());
        return handleExceptionInternal(e, ex, new HttpHeaders(), e.getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        var errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> {
                    var unexpectedError = new UnexpectedError();
                    unexpectedError.setMessage(error.getDefaultMessage());
                    return unexpectedError;
                })
                .collect(Collectors.toList());
        return new ResponseEntity<>(errors, headers, status);
    }

}
