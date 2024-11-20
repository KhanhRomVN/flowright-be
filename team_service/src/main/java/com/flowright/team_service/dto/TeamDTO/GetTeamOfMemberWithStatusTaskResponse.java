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
public class GetTeamOfMemberWithStatusTaskResponse {
    private UUID id;
    private String name;
    private String description;
    private String type;
    private String status;
    private int totalTodo;
    private int totalInprogress;
    private int totalOverdue;
    private int totalDone;
    private int totalOverdone;
    private int totalCancel;
}
