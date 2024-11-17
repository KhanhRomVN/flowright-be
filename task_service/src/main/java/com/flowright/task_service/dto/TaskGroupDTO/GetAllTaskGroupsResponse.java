package com.flowright.task_service.dto.TaskGroupDTO;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllTaskGroupsResponse {
    private UUID id;
    private String name;
    private String description;
}
