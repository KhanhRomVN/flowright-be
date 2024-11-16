package com.flowright.task_service.dto.TaskDTO.UpdateTaskDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEndDateTaskRequest {
    private String taskId;
    private String endDate;
}
