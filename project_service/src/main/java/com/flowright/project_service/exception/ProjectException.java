package com.flowright.project_service.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ProjectException extends RuntimeException {
    private final HttpStatus status;
    private final String message;

    public ProjectException(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }
}
