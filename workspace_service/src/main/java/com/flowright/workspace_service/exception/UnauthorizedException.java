package com.flowright.workspace_service.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class UnauthorizedException extends RuntimeException {
    private final HttpStatus status;

    public UnauthorizedException(String message) {
        super(message);
        this.status = HttpStatus.UNAUTHORIZED;
    }
}