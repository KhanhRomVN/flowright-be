package com.flowright.task_service.dto.TaskAssignmentDTO;

import jakarta.validation.constraints.NotBlank;

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

    @NotBlank(message = "Team ID is required")
    private String teamId;
}
