package com.flowright.project_service.dto.ProjectDTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateProjectRequest {
    private String name;
    private String description;
    private String ownerId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String teamId;
}
