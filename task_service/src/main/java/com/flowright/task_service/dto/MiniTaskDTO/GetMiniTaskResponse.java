package com.flowright.task_service.dto.MiniTaskDTO;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetMiniTaskResponse {
    private UUID miniTaskId;
    private String miniTaskName;
    private String miniTaskDescription;
    private String miniTaskStatus;
    private UUID miniTaskMemberId;
    private String miniTaskMemberUsername;
    private String miniTaskMemberEmail;
    private UUID taskId;
}
