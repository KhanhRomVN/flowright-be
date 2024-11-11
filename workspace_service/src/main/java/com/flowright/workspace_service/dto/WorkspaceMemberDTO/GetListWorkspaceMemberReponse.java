package com.flowright.workspace_service.dto.WorkspaceMemberDTO;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetListWorkspaceMemberReponse {
    private UUID id;
    private String name;
    private UUID ownerId;
    private String ownerUsername;
    private Integer totalMembers;
}
