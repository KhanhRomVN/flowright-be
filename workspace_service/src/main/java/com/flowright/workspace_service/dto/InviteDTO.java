package com.flowright.workspace_service.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InviteDTO {
    private UUID id;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    private String otp;

    @NotNull(message = "Role ID cannot be null")
    private UUID roleId;

    private String status;

    @NotNull(message = "Workspace ID cannot be null")
    private UUID workspaceId;

    private LocalDateTime expiresAt;
}
