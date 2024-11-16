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
public class GetAllTaskTeamResponse {
    private UUID taskId;
    private String taskName;
    private String taskDescription;
    private UUID creatorId;
    private String creatorUsername;
    private String priority;
    private UUID projectId;
    private String projectName;
    private UUID taskGroupId;
    private String taskGroupName;
    private UUID nextTaskId;
    private String nextTaskName;
    private UUID previousTaskId;
    private String previousTaskName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
    private List<GetTaskAssignmentResponse> taskAssignments;
}
