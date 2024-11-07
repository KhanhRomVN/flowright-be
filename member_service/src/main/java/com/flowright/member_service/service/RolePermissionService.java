package com.flowright.member_service.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flowright.member_service.dto.PermissionDTO.PermissionResponse;
import com.flowright.member_service.entity.Permission;
import com.flowright.member_service.entity.Role;
import com.flowright.member_service.entity.RolePermission;
import com.flowright.member_service.repository.PermissionRepository;
import com.flowright.member_service.repository.RolePermissionRepository;
import com.flowright.member_service.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RolePermissionService {
    private final RolePermissionRepository rolePermissionRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Transactional
    public void assignPermissionToRole(UUID roleId, UUID permissionId) {
        // Check if role exists
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Role not found"));

        // Check if permission exists
        Permission permission = permissionRepository
                .findById(permissionId)
                .orElseThrow(() -> new RuntimeException("Permission not found"));

        // Check if the role already has this permission
        if (rolePermissionRepository.existsByRoleIdAndPermissionId(roleId, permissionId)) {
            throw new RuntimeException("Role already has this permission");
        }

        // Create and save the role-permission relationship
        RolePermission rolePermission = RolePermission.builder()
                .roleId(role.getId())
                .permissionId(permission.getId())
                .build();
        rolePermissionRepository.save(rolePermission);
    }

    @Transactional
    public void removePermissionFromRole(UUID roleId, UUID permissionId) {
        // Check if role exists
        if (!roleRepository.existsById(roleId)) {
            throw new RuntimeException("Role not found");
        }

        // Check if permission exists
        if (!permissionRepository.existsById(permissionId)) {
            throw new RuntimeException("Permission not found");
        }

        rolePermissionRepository.deleteByRoleIdAndPermissionId(roleId, permissionId);
    }

    // public List<PermissionResponse> getRolePermissions(UUID roleId) {
    //     Role role = roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Role not found"));

    //     return rolePermissionRepository.findByRoleId(roleId).stream()
    //             .map(rolePermission -> toPermissionResponse(rolePermission.getPermission()))
    //             .collect(Collectors.toList());
    // }

    private PermissionResponse toPermissionResponse(Permission permission) {
        return PermissionResponse.builder()
                .id(permission.getId())
                .name(permission.getName())
                .description(permission.getDescription())
                .build();
    }
}
