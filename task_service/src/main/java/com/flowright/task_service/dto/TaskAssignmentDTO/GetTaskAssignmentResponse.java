package com.flowright.task_service.dto.TaskAssignmentDTO;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetTaskAssignmentResponse {
    private UUID taskId;
    private UUID assigneeId;
    private String assigneeUsername;
}
