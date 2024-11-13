package com.flowright.task_service.dto.TaskDTO;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

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
    @NotBlank(message = "Name is required")
    private String name;

    private String description;

    @NotBlank(message = "Owner ID is required")
    private String ownerId;

    @NotBlank(message = "Project ID is required")
    private String projectId;

    @NotBlank(message = "Priority is required")
    private String priority;

    @NotBlank(message = "Start date is required")
    private String startDate;

    private String endDate;

    private String previousTaskId;

    private String nextTaskId;

    private String taskGroupId;

    private List<CreateTaskAssignmentRequest> taskAssignments;

    private List<CreateTaskLinkRequest> taskLinks;

    private List<CreateMiniTaskRequest> miniTasks;
}
