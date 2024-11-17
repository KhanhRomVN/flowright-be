package com.flowright.project_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flowright.project_service.dto.ProjectDTO.CreateProjectRequest;
import com.flowright.project_service.dto.ProjectDTO.CreateProjectResponse;
import com.flowright.project_service.dto.ProjectDTO.GetAllProjectsResponse;
import com.flowright.project_service.entity.Project;
import com.flowright.project_service.kafka.consumer.GetMemberInfoConsumer;
import com.flowright.project_service.kafka.producer.GetMemberInfoProducer;
import com.flowright.project_service.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {
    @Autowired
    private final ProjectRepository projectRepository;

    @Autowired
    private final ProjectAssignmentService projectAssignmentService;

    @Autowired
    private final GetMemberInfoConsumer getMemberInfoConsumer;

    @Autowired
    private final GetMemberInfoProducer getMemberInfoProducer;

    public CreateProjectResponse createProject(CreateProjectRequest request, UUID workspaceId, UUID creatorId) {
        Project project = Project.builder()
                .name(request.getName())
                .description(request.getDescription())
                .workspaceId(workspaceId)
                .ownerId(UUID.fromString(request.getOwnerId()))
                .creatorId(creatorId)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .status("in_progress")
                .build();

        projectRepository.save(project);

        projectAssignmentService.createProjectAssignment(project.getId(), UUID.fromString(request.getOwnerId()));

        return CreateProjectResponse.builder()
                .message("Project created successfully")
                .build();
    }

    public List<GetAllProjectsResponse> getAllProjects(UUID workspaceId) {
        List<Project> projects = projectRepository.findByWorkspaceId(workspaceId);
        List<GetAllProjectsResponse> response = new ArrayList<>();
        for (Project project : projects) {
            getMemberInfoProducer.sendMessage(project.getOwnerId());
            String getMemberInfoConsumerResponse = getMemberInfoConsumer.getResponse();
            String[] responseSplit = getMemberInfoConsumerResponse.split(",");
            String ownerUsername = responseSplit[0];

            getMemberInfoProducer.sendMessage(project.getCreatorId());
            String _getMemberInfoConsumerResponse = getMemberInfoConsumer.getResponse();
            String[] _responseSplit = _getMemberInfoConsumerResponse.split(",");
            String creatorUsername = _responseSplit[0];

            response.add(GetAllProjectsResponse.builder()
                    .id(project.getId())
                    .name(project.getName())
                    .description(project.getDescription())
                    .ownerId(project.getOwnerId())
                    .ownerUsername(ownerUsername)
                    .creatorId(project.getCreatorId())
                    .creatorUsername(creatorUsername)
                    .startDate(project.getStartDate())
                    .endDate(project.getEndDate())
                    .status(project.getStatus())
                    .build());
        }
        return response;
    }

    public Project getProjectById(UUID projectId) {
        return projectRepository.findById(projectId).get();
    }
}
