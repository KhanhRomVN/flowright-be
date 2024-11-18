package com.flowright.team_service.dto.TeamDTO;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetTeamOfWorkspaceResponse {
    private UUID id;
    private String name;
    private String description;
    private UUID leaderId;
    private String leaderUsername;
    private int totalMember;
    private String status;
}
