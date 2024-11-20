package com.flowright.task_service.dto.TaskDTO;

import java.util.List;

import com.flowright.task_service.dto.MiniTaskDTO.CreateMiniTaskRequest;
import com.flowright.task_service.dto.TaskAssignmentDTO.CreateTaskAssignmentRequest;
import com.flowright.task_service.dto.TaskLinkDTO.CreateTaskLinkRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskRequest {
    private String name;

    private String description;

    private String projectId;

    private String priority;

    private String teamId;

    private String startDate;

    private String endDate;

    private String taskGroupId;

    private List<CreateMiniTaskRequest> miniTasks;

    private List<CreateTaskAssignmentRequest> taskAssignments;

    private List<CreateTaskLinkRequest> taskLinks;
}
