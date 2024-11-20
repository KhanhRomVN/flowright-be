package com.flowright.task_service.dto.TaskDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetTotalStatusTaskResponse {
    private int totalTodo;
    private int totalInProgress;
    private int totalDone;
    private int totalOverdue;
}
