package com.flipflow.exception;

public class WorkoutTypeAlreadyPresentException extends RuntimeException {
    public WorkoutTypeAlreadyPresentException(String message) {
        super(message);
    }
}
