package com.flowright.workspace_service.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private String code;
    private String message;
    private List<String> details;

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}