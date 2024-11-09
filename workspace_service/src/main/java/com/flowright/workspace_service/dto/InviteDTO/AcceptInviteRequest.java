package com.flowright.workspace_service.dto.InviteDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AcceptInviteRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String email;

    @NotBlank
    private String workspaceId;
    @NotBlank
    private String token;
}
