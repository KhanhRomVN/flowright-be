package com.flowright.project_service.dto.ProjectDTO;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllProjectsResponse {
    private UUID id;
    private String name;
    private String description;
    private UUID ownerId;
    private String ownerUsername;
    private UUID creatorId;
    private String creatorUsername;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
}
