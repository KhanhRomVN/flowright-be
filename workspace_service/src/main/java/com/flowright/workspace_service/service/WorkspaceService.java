package com.flowright.workspace_service.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.flowright.workspace_service.dto.WorkspaceDTO.CreateWorkspaceReponse;
import com.flowright.workspace_service.dto.WorkspaceDTO.CreateWorkspaceRequest;
import com.flowright.workspace_service.dto.WorkspaceDTO.GetListWorkspaceReponse;
import com.flowright.workspace_service.entity.Workspace;
import com.flowright.workspace_service.kafka.consumer.GetTotalMemberConsumer;
import com.flowright.workspace_service.kafka.consumer.GetUserInfoConsumer;
import com.flowright.workspace_service.kafka.producer.CreateWorkspaceProducer;
import com.flowright.workspace_service.kafka.producer.GetTotalMemberProducer;
import com.flowright.workspace_service.kafka.producer.GetUserInfoProducer;
import com.flowright.workspace_service.repository.WorkspaceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkspaceService {
    private final WorkspaceRepository workspaceRepository;
    private final CreateWorkspaceProducer createWorkspaceProducer;
    private final GetUserInfoConsumer getUserInfoConsumer;
    private final GetUserInfoProducer getUserInfoProducer;
    private final GetTotalMemberConsumer getTotalMemberConsumer;
    private final GetTotalMemberProducer getTotalMemberProducer;

    public CreateWorkspaceReponse createWorkspace(CreateWorkspaceRequest requestBody, UUID ownerId) {
        // check if the ownerId has same workspace_name
        boolean existingWorkspace = workspaceRepository.existsByNameAndOwnerId(requestBody.getName(), ownerId);
        if (existingWorkspace) {
            throw new RuntimeException("Workspace name already exists");
        }

        getUserInfoProducer.sendMessage(ownerId);

        String response = getUserInfoConsumer.getResponse();

        String[] userParts = response.split(",");
        String username = userParts[0];
        String email = userParts[1];

        Workspace workspace =
                Workspace.builder().name(requestBody.getName()).ownerId(ownerId).build();

        Workspace savedWorkspace = workspaceRepository.save(workspace);

        createWorkspaceProducer.sendMessage(savedWorkspace.getId(), savedWorkspace.getOwnerId(), username, email);

        return CreateWorkspaceReponse.builder()
                .id(savedWorkspace.getId())
                .name(savedWorkspace.getName())
                .ownerId(savedWorkspace.getOwnerId())
                .build();
    }

    public List<GetListWorkspaceReponse> getListWorkspacesByOwnerId(UUID ownerId) {
        List<Workspace> workspaces = workspaceRepository.findByOwnerId(ownerId);
        List<GetListWorkspaceReponse> response = workspaces.stream()
                .map(workspace -> GetListWorkspaceReponse.builder()
                        .id(workspace.getId())
                        .name(workspace.getName())
                        .ownerId(workspace.getOwnerId())
                        .build())
                .collect(Collectors.toList());

        for (GetListWorkspaceReponse workspace : response) {
            getTotalMemberProducer.sendMessage(workspace.getId());
            Integer totalMember = getTotalMemberConsumer.getTotalMember();
            workspace.setTotalMembers(totalMember);
        }

        for (GetListWorkspaceReponse workspace : response) {
            getUserInfoProducer.sendMessage(workspace.getOwnerId());
            String userResponse = getUserInfoConsumer.getResponse();
            String[] userParts = userResponse.split(",");
            String username = userParts[0];
            workspace.setOwnerUsername(username);
        }
        return response;
    }

    public Workspace findWorkspaceById(UUID workspaceId) {
        return workspaceRepository.findById(workspaceId).orElseThrow(() -> new RuntimeException("Workspace not found"));
    }
}
