package com.flowright.other_service.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class OtherException extends RuntimeException {
    private final HttpStatus status;
    private final String message;

    public OtherException(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }
}
