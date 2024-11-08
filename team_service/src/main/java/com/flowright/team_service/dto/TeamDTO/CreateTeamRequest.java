package com.flowright.team_service.dto.TeamDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTeamRequest {
    @NotNull(message = "Leader ID is required")
    private Long leaderId;

    @NotBlank(message = "Team name is required")
    private String name;

    private String description;

    @NotBlank(message = "Team type is required")
    private String type;
}
