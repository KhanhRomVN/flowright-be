package com.flowright.auth_service.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    private String access_token;
    private String refresh_token;
    private UUID id;
    private String username;
    private String email;
}
