package com.flowright.task_service.dto.TaskGroupDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskGroupRequest {
    private String name;
    private String description;
    private String projectId;
}
