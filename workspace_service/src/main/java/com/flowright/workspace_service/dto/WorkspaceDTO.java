package com.flowright.workspace_service.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceDTO {
    private UUID id;

    @NotBlank(message = "Workspace name cannot be blank")
    @Size(min = 1, max = 50, message = "Workspace name must be between 1 and 50 characters")
    private String name;

    private UUID ownerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
