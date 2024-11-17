package com.flowright.team_service.dto.TeamMemberDTO;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetListMemberTeamResponse {
    private UUID id;
    private UUID teamId;
    private UUID memberId;
    private String memberUsername;
    private String memberEmail;
}
