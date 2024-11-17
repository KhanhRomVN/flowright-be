package com.flowright.task_service.dto.TaskDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.flowright.task_service.dto.TaskAssignmentDTO.GetTaskAssignmentResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllTaskProjectResponse {
    private UUID taskId;
    private String taskName;
    private String priority;
    private UUID projectId;
    private UUID taskGroupId;
    private String taskGroupName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
    private List<GetTaskAssignmentResponse> taskAssignments;
}
