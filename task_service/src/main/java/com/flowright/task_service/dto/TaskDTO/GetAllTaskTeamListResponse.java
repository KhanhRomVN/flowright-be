package com.flowright.task_service.dto.TaskDTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllTaskTeamListResponse {
    private List<GetAllTaskTeamResponse> tasks;
}
