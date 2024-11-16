package com.flowright.task_service.dto.TaskLogDTO;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetTaskLogResponse {
    private UUID taskLogId;
    private String taskLogTitle;
    private String taskLogDescription;
    private LocalDateTime taskLogDate;
}
