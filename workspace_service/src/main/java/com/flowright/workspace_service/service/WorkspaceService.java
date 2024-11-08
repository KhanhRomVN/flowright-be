package com.flowright.workspace_service.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.flowright.workspace_service.dto.WorkspaceDTO.CreateWorkspaceReponse;
import com.flowright.workspace_service.dto.WorkspaceDTO.CreateWorkspaceRequest;
import com.flowright.workspace_service.entity.Workspace;
import com.flowright.workspace_service.repository.WorkspaceRepository;
import com.flowright.workspace_service.service.kafka.CreateWorkspaceProducer;
import com.flowright.workspace_service.service.kafka.GetUserInfoConsumer;
import com.flowright.workspace_service.service.kafka.GetUserInfoProducer;

import lombok.RequiredArgsConstructor;

@Service    
@RequiredArgsConstructor
public class WorkspaceService {
    private final WorkspaceRepository workspaceRepository;
    private final CreateWorkspaceProducer createWorkspaceProducer;
    private final GetUserInfoConsumer getUserInfoConsumer;
    private final GetUserInfoProducer getUserInfoProducer;

    public CreateWorkspaceReponse createWorkspace(CreateWorkspaceRequest requestBody, UUID ownerId) {
        // check if the ownerId has same workspace_name
        boolean existingWorkspace = workspaceRepository.existsByNameAndOwnerId(requestBody.getName(), ownerId);
        if (existingWorkspace) {
            throw new RuntimeException("Workspace name already exists");
        }

        // Send a message to the kafka topic to get user info(username, email)
        getUserInfoProducer.sendMessage(ownerId);
        
        // Receive a message from the kafka topic to get user info(username, email)
        String response = getUserInfoConsumer.getResponse();
        System.out.println("fuck" + response);
        
        // Split the response to get username and email
        String[] userParts = response.split(",");
        String username = userParts[0];
        String email = userParts[1];
        
        // Create a new Workspace entity from the request body
        Workspace workspace = Workspace.builder()
        .name(requestBody.getName())
        .ownerId(ownerId)
        .build();
        
        // Save the workspace to the repository
        Workspace savedWorkspace = workspaceRepository.save(workspace);
        
        // Send a message to the kafka topic
        createWorkspaceProducer.sendMessage(savedWorkspace.getId(), savedWorkspace.getOwnerId(), username, email);

        // Create and return the response DTO
        return CreateWorkspaceReponse.builder()
        .id(savedWorkspace.getId())
        .name(savedWorkspace.getName())
                .ownerId(savedWorkspace.getOwnerId())
                .build();
    }
}
