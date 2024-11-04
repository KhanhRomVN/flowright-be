package com.flowright.member_service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flowright.member_service.dto.CreateRoleRequest;
import com.flowright.member_service.dto.RoleResponse;
import com.flowright.member_service.dto.UpdateRoleRequest;
import com.flowright.member_service.entity.Role;
import com.flowright.member_service.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    @Transactional
    public RoleResponse createRole(CreateRoleRequest request) {
        Role role = Role.builder()
                .name(request.getName())
                .description(request.getDescription())
                .workspaceId(request.getWorkspaceId())
                .isDefault(request.getIsDefault())
                .build();

        Role savedRole = roleRepository.save(role);
        return toRoleResponse(savedRole);
    }

    @Transactional
    public RoleResponse updateRole(Long id, UpdateRoleRequest request) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found"));

        if (request.getName() != null) {
            role.setName(request.getName());
        }
        if (request.getDescription() != null) {
            role.setDescription(request.getDescription());
        }
        if (request.getIsDefault() != null) {
            role.setIsDefault(request.getIsDefault());
        }

        Role updatedRole = roleRepository.save(role);
        return toRoleResponse(updatedRole);
    }

    @Transactional
    public void deleteRole(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new RuntimeException("Role not found");
        }
        roleRepository.deleteById(id);
    }

    public RoleResponse getRoleById(Long id) {
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
                .isDefault(role.getIsDefault())
                .build();
    }

    public RoleResponse getAdminRoleByWorkspaceId(Long workspaceId) {
        Role role = roleRepository
                .findByWorkspaceIdAndNameAndIsDefault(workspaceId, "Admin", false)
                .orElseThrow(() -> new RuntimeException("Admin role not found"));
        return toRoleResponse(role);
    }

    public Page<RoleResponse> getAllRolesByWorkspaceId(Long workspaceId) {
        return roleRepository.findByWorkspaceId(workspaceId, Pageable.unpaged()).map(this::toRoleResponse);
    }
}
