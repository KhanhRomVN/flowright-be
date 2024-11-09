package com.flowright.task_service.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class TaskException extends RuntimeException {
    private final HttpStatus status;
    private final String message;

    public TaskException(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }
}
