package com.thinkforge.quiz_service.exception;

public class EvaluationNotFoundException extends RuntimeException {
    public EvaluationNotFoundException(String message) {
        super(message);
    }
}