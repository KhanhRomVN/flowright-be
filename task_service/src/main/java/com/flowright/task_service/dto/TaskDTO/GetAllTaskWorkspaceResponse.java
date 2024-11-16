package com.flowright.task_service.dto.TaskDTO;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetAllTaskWorkspaceResponse {
    private List<GetTaskWorkspaceResponse> tasks;
}
