package com.flowright.auth_service.dto;

import java.util.Map;

import lombok.Data;

@Data
public class ErrorResponse {
    private final int status;
    private final String message;
    private final Map<String, String> errors;

    public ErrorResponse(int status, String message, Map<String, String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }
}
