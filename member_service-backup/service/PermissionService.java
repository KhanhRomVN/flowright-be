package com.flowright.member_service.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.flowright.member_service.dto.PermissionDTO.CreatePermissionRequest;
import com.flowright.member_service.dto.PermissionDTO.PermissionResponse;
import com.flowright.member_service.entity.Permission;
import com.flowright.member_service.repository.PermissionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PermissionService {
    private final PermissionRepository permissionRepository;

    public PermissionResponse createPermission(CreatePermissionRequest request) {
        if (permissionRepository.existsByName(request.getName())) {
            throw new RuntimeException("Permission with this name already exists");
        }

        Permission permission = Permission.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        Permission savedPermission = permissionRepository.save(permission);
        return toPermissionResponse(savedPermission);
    }

    public void deletePermission(UUID id) {
        if (!permissionRepository.existsById(id)) {
            throw new RuntimeException("Permission not found");
        }
        permissionRepository.deleteById(id);
    }

    public PermissionResponse getPermissionById(UUID id) {
        Permission permission =
                permissionRepository.findById(id).orElseThrow(() -> new RuntimeException("Permission not found"));
        return toPermissionResponse(permission);
    }

    public Page<PermissionResponse> getAllPermissions(Pageable pageable) {
        return permissionRepository.findAll(pageable).map(this::toPermissionResponse);
    }

    private PermissionResponse toPermissionResponse(Permission permission) {
        return PermissionResponse.builder()
                .id(permission.getId())
                .name(permission.getName())
                .description(permission.getDescription())
                .build();
    }
}
