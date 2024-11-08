package com.flowright.workspace_service.dto.WorkspaceDTO;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateWorkspaceReponse {
    private UUID id;
    private String name;
    private UUID ownerId;
}
