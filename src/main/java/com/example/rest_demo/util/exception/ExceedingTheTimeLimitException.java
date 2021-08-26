package com.example.rest_demo.util.exception;

public class ExceedingTheTimeLimitException extends Exception {
    public ExceedingTheTimeLimitException(String errorMessage) {
        super(errorMessage);
    }
}
