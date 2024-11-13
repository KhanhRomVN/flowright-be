package com.flowright.team_service.dto.TeamMemberDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddMemberRequest {
    private String memberId;
    private String teamId;
}
