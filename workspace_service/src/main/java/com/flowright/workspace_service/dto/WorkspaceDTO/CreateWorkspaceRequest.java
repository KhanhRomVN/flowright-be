package com.flowright.workspace_service.dto.WorkspaceDTO;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateWorkspaceRequest {
    @NotBlank(message = "Workspace name cannot be blank")
    private String name;
}
