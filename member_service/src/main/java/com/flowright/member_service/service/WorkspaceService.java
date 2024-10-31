package com.flowright.member_service.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.flowright.member_service.dto.WorkspaceDTO;
import com.flowright.member_service.entity.Workspace;
import com.flowright.member_service.exception.ResourceNotFoundException;
import com.flowright.member_service.exception.UnauthorizedException;
import com.flowright.member_service.repository.WorkspaceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkspaceService {
    private final WorkspaceRepository workspaceRepository;

    public WorkspaceDTO createWorkspace(WorkspaceDTO workspaceDTO) {
        Workspace workspace = Workspace.builder()
                .name(workspaceDTO.getName())
                .ownerId(workspaceDTO.getOwnerId())
                .build();

        workspace = workspaceRepository.save(workspace);
        return convertToDTO(workspace);
    }

    public List<WorkspaceDTO> getWorkspacesByOwnerId(Long ownerId) {
        return workspaceRepository.findByOwnerId(ownerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public WorkspaceDTO updateWorkspace(Long id, WorkspaceDTO workspaceDTO, Long userId) {
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

    public void deleteWorkspace(Long id, Long userId) {
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
