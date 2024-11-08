package com.flowright.team_service.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class TeamException extends RuntimeException {
    private final HttpStatus status;
    private final String message;

    public TeamException(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }
}
