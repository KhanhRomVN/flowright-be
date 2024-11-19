package com.flowright.team_service.dto.TeamMemberDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteMemberTeamRequest {
    private String memberId;
    private String teamId;
}
