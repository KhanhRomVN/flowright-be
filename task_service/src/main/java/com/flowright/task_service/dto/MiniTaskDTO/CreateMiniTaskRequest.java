package com.flowright.task_service.dto.MiniTaskDTO;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateMiniTaskRequest {
    @NotBlank(message = "Name is required")
    private String name;

    private String description;

    @NotBlank(message = "Team ID is required")
    private String teamId;

    @NotBlank(message = "Member ID is required")
    private String memberId;
}
