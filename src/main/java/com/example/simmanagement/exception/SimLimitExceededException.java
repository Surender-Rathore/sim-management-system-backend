package com.example.simmanagement.exception;

public class SimLimitExceededException extends RuntimeException {
    public SimLimitExceededException(String message) {
        super(message);
    }
}