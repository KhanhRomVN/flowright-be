package com.flowright.workspace_service.dto.InviteDTO;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetInviteResponse {
    private UUID id;
    private UUID roleId;
    private String roleName;
    private String token;
    private String status;
    private LocalDateTime expiresAt;
}
