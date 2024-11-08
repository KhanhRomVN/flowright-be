package com.flowright.user_service.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class UserException extends RuntimeException {
    private final HttpStatus status;
    private final String message;

    public UserException(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }
}
