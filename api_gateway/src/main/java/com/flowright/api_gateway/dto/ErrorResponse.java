package com.flowright.api_gateway.dto;

import java.util.Map;
import lombok.Data;

@Data
public class ErrorResponse {
    private final int status;
    private final String message;
    private final long timestamp;
    private Map<String, String> errors;

    public ErrorResponse(int status, String message, long timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    public ErrorResponse(int status, String message, long timestamp, Map<String, String> errors) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
        this.errors = errors;
    }
}