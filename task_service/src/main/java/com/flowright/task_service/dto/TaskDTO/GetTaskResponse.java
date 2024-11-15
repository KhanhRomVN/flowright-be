package com.flowright.task_service.dto.TaskDTO;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetTaskResponse {
    private UUID taskId;
    private String name;
    private String description;
    private String priority;
}
