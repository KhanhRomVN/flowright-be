package com.flowright.task_service.dto.TaskDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.flowright.task_service.dto.MiniTaskDTO.GetMiniTaskResponse;
import com.flowright.task_service.dto.TaskAssignmentDTO.GetTaskAssignmentResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetMemberStatusTaskResponse {
    private UUID id;
    private String name;
    private String description;
    private String status;
    private String priority;
    private UUID projectId;
    private String projectName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private UUID teamId;
    private String teamName;
    private List<GetTaskAssignmentResponse> taskAssignments;
    private List<GetMiniTaskResponse> miniTasks;
}
