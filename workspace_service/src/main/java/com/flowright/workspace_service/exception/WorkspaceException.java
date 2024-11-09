package com.flowright.workspace_service.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class WorkspaceException extends RuntimeException {
    private final HttpStatus status;
    private final String message;

    public WorkspaceException(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }
}
