package com.flowright.member_service.dto.RolePermissionDTO;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignPermissionRequest {
    @NotNull(message = "Permission ID is required")
    private UUID permissionId;
}
