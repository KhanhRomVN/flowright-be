package com.flowright.project_service.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flowright.project_service.dto.ProjectDTO.CreateProjectRequest;
import com.flowright.project_service.dto.ProjectDTO.CreateProjectResponse;
import com.flowright.project_service.dto.ProjectDTO.GetAllProjectsResponse;
import com.flowright.project_service.entity.Project;
import com.flowright.project_service.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {
    @Autowired
    private final ProjectRepository projectRepository;

    @Autowired
    private final ProjectAssignmentService projectAssignmentService;

    public CreateProjectResponse createProject(CreateProjectRequest request, UUID workspaceId, UUID creatorId) {
        Project project = Project.builder()
                .name(request.getName())
                .description(request.getDescription())
                .workspaceId(workspaceId)
                .ownerId(creatorId)
                .creatorId(creatorId)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .status("todo")
                .build();

        projectRepository.save(project);

        projectAssignmentService.createProjectAssignment(project.getId(), UUID.fromString(request.getTeamId()));

        return CreateProjectResponse.builder()
                .message("Project created successfully")
                .build();
    }

    public GetAllProjectsResponse getAllProjects(UUID workspaceId) {
        List<Project> projects = projectRepository.findByWorkspaceId(workspaceId);
        return GetAllProjectsResponse.builder().projects(projects).build();
    }

    public Project getProjectById(UUID projectId) {
        return projectRepository.findById(projectId).get();
    }
}
