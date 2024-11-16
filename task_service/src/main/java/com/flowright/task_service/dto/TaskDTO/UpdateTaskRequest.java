package com.flowright.task_service.dto.TaskDTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTaskRequest {
    private String name;
    private String description;
    private String priority;
    private String status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
