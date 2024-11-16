package com.flowright.task_service.dto.MiniTaskDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateMiniTaskRequest {
    private String name;
    private String description;
    private String memberId;
}
