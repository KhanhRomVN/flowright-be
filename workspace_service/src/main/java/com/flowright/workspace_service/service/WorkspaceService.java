package com.flowright.workspace_service.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors; 

import org.springframework.stereotype.Service;

import com.flowright.workspace_service.dto.WorkspaceDTO;
import com.flowright.workspace_service.entity.Workspace;
import com.flowright.workspace_service.exception.ResourceNotFoundException;
import com.flowright.workspace_service.exception.UnauthorizedException;
import com.flowright.workspace_service.repository.WorkspaceRepository;


import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class WorkspaceService {
    private final WorkspaceRepository workspaceRepository;
    
    
    // @Transactional
    // public WorkspaceDTO createWorkspace(WorkspaceDTO workspaceDTO, String token) {
        
    //     return convertToDTO(workspace);
    // }

    public List<WorkspaceDTO> getWorkspacesByOwnerId(UUID ownerId) {
        return workspaceRepository.findByOwnerId(ownerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public WorkspaceDTO updateWorkspace(UUID id, WorkspaceDTO workspaceDTO, UUID userId) {
        Workspace workspace = workspaceRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workspace not found"));

        if (!workspace.getOwnerId().equals(userId)) {
            throw new UnauthorizedException("You don't have permission to update this workspace");
        }

        workspace.setName(workspaceDTO.getName());
        workspace = workspaceRepository.save(workspace);
        return convertToDTO(workspace);
    }

    public void deleteWorkspace(UUID id, UUID userId) {
        Workspace workspace = workspaceRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workspace not found"));

        if (!workspace.getOwnerId().equals(userId)) {
            throw new UnauthorizedException("You don't have permission to delete this workspace");
        }

        workspaceRepository.delete(workspace);
    }

    private WorkspaceDTO convertToDTO(Workspace workspace) {
        return WorkspaceDTO.builder()
                .id(workspace.getId())
                .name(workspace.getName())
                .ownerId(workspace.getOwnerId())
                .build();
    }
}
