package com.flowright.task_service.dto.TaskDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.flowright.task_service.dto.MiniTaskDTO.GetMiniTaskResponse;
import com.flowright.task_service.dto.TaskAssignmentDTO.GetTaskAssignmentResponse;
import com.flowright.task_service.dto.TaskCommentDTO.GetTaskCommentResponse;
import com.flowright.task_service.dto.TaskLinkDTO.GetTaskLinkResponse;
import com.flowright.task_service.dto.TaskLogDTO.GetTaskLogResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetTaskResponse {
    private UUID taskId;
    private UUID teamId;
    private String teamName;
    private String taskName;
    private String taskDescription;
    private String priority;
    private UUID creatorId;
    private String creatorUsername;
    private String creatorEmail;
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
    private List<GetMiniTaskResponse> miniTasks;
    private List<GetTaskAssignmentResponse> taskAssignments;
    private List<GetTaskLinkResponse> taskLinks;
    private List<GetTaskCommentResponse> taskComments;
    private List<GetTaskLogResponse> taskLogs;
}
