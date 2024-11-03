package com.flowright.auth_service.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class AuthException extends RuntimeException {
    private final HttpStatus status;
    private final String message;

    public AuthException(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }
}
