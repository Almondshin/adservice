package org.example.adservice.controller.exception;

import org.example.adservice.controller.exception.exceptions.DuplicateAdNameException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateAdNameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleDuplicateAdName(DuplicateAdNameException ex) {
        return Map.of("error", ex.getMessage());
    }
}
