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
public class GetListWorkspaceReponse {
    private UUID id;
    private String name;
    private UUID ownerId;
    private String ownerUsername;
    private int totalMembers;

    public void setTotalMembers(int totalMembers) {
        this.totalMembers = totalMembers;
    }
}
