package com.flowright.member_service.dto.RoleDTO;

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
public class CreateRoleRequest {
    @NotBlank(message = "Role name is required")
    private String name;

    private String description;

    @NotNull(message = "Workspace ID is required")
    private Long workspaceId;
}
