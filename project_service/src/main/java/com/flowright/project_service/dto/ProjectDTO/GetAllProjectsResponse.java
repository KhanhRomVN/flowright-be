package com.flowright.project_service.dto.ProjectDTO;

import java.util.List;

import com.flowright.project_service.entity.Project;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllProjectsResponse {
    private List<Project> projects;
}
