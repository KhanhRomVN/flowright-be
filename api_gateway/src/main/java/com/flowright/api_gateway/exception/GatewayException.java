package com.flowright.api_gateway.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;

@Getter
public class GatewayException extends RuntimeException {
    private final HttpStatus status;
    private final String message;

    public GatewayException(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }
}