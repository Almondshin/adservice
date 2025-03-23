package org.example.adservice.controller.exception.exceptions;

public class DuplicateAdNameException extends RuntimeException {
    public DuplicateAdNameException(String message) {
        super(message);
    }
}
