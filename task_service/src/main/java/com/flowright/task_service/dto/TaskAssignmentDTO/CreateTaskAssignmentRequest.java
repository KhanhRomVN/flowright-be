package com.flowright.task_service.dto.TaskAssignmentDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskAssignmentRequest {
    private String memberId;
    private String teamId;
}
