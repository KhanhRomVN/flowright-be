package com.flowright.team_service.dto.TeamDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTeamResponse {
    private Long id;
    private String name;
    private String description;
    private String type;
    private String status;
    private Long leaderId;
}
