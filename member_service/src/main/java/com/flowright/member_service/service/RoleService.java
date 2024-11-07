package com.flowright.member_service.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flowright.member_service.dto.RoleDTO.CreateRoleRequest;
import com.flowright.member_service.dto.RoleDTO.RoleResponse;
import com.flowright.member_service.dto.RoleDTO.UpdateRoleRequest;
import com.flowright.member_service.entity.Role;
import com.flowright.member_service.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    @Transactional
    public RoleResponse createRole(CreateRoleRequest request, UUID workspaceId) {
        Role role = Role.builder()
                .name(request.getName())
                .description(request.getDescription())
                .workspaceId(workspaceId)
                .build();

        Role savedRole = roleRepository.save(role);
        return toRoleResponse(savedRole);
    }

    @Transactional
    public RoleResponse updateRole(UUID id, UpdateRoleRequest request) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found"));

        if (request.getName() != null) {
            role.setName(request.getName());
        }
        if (request.getDescription() != null) {
            role.setDescription(request.getDescription());
        }

        Role updatedRole = roleRepository.save(role);
        return toRoleResponse(updatedRole);
    }

    @Transactional
    public void deleteRole(UUID id) {
        if (!roleRepository.existsById(id)) {
            throw new RuntimeException("Role not found");
        }
        roleRepository.deleteById(id);
    }

    public RoleResponse getRoleById(UUID id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found"));
        return toRoleResponse(role);
    }

    public Page<RoleResponse> getAllRoles(Pageable pageable) {
        return roleRepository.findAll(pageable).map(this::toRoleResponse);
    }

    private RoleResponse toRoleResponse(Role role) {
        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .workspaceId(role.getWorkspaceId())
                .build();
    }

    public RoleResponse getAdminRoleByWorkspaceId(UUID workspaceId) {
        Role role = roleRepository
                .findByWorkspaceIdAndName(workspaceId, "Admin")
                .orElseThrow(() -> new RuntimeException("Admin role not found"));
        return toRoleResponse(role);
    }

    public Page<RoleResponse> getAllRolesByWorkspaceId(UUID workspaceId) {
        return roleRepository.findByWorkspaceId(workspaceId, Pageable.unpaged()).map(this::toRoleResponse);
    }

    public String getRoleNameById(UUID id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found"));
        return role.getName();
    }
}
