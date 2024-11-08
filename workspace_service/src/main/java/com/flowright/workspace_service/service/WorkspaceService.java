package com.flowright.workspace_service.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.flowright.workspace_service.dto.WorkspaceDTO.CreateWorkspaceReponse;
import com.flowright.workspace_service.dto.WorkspaceDTO.CreateWorkspaceRequest;
import com.flowright.workspace_service.entity.Workspace;
import com.flowright.workspace_service.repository.WorkspaceRepository;
import com.flowright.workspace_service.service.kafka.CreateWorkspaceProducer;

import lombok.RequiredArgsConstructor;


@Service    
@RequiredArgsConstructor
public class WorkspaceService {
    private final WorkspaceRepository workspaceRepository;
    private final CreateWorkspaceProducer createWorkspaceProducer;
    public CreateWorkspaceReponse createWorkspace(CreateWorkspaceRequest requestBody, UUID ownerId) {
        // Create a new Workspace entity from the request body
        Workspace workspace = Workspace.builder()
                .name(requestBody.getName())
                .ownerId(ownerId)
                .build();
    
        // Save the workspace to the repository
        Workspace savedWorkspace = workspaceRepository.save(workspace);

        String name = "Admin";
        String description = "Admin Role Can Access All Resources";
        
        // Send a message to the kafka topic
        createWorkspaceProducer.sendMessage(savedWorkspace.getId(), savedWorkspace.getOwnerId(), name, description);
    
        // Create and return the response DTO
        return CreateWorkspaceReponse.builder()
                .id(savedWorkspace.getId())
                .name(savedWorkspace.getName())
                .ownerId(savedWorkspace.getOwnerId())
                .build();
    }
}
