package com.flowright.workspace_service.dto.InviteDTO;

import java.time.LocalDateTime;
import java.util.UUID;

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
public class CreateInviteRequest {
    @NotNull(message = "ID cannot be null")
    private UUID id;

    @NotBlank(message = "Email cannot be blank")
    private String email;

    private String otp;

    @NotNull(message = "Role ID cannot be null")
    private UUID roleId;

    @NotNull(message = "Status cannot be null")
    private String status;

    @NotNull(message = "Workspace ID cannot be null")
    private UUID workspaceId;

    @NotNull(message = "Expires at cannot be null")
    private LocalDateTime expiresAt;
}
