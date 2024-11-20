package com.flowright.member_service.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flowright.member_service.dto.RoleDTO.CreateRoleRequest;
import com.flowright.member_service.dto.RoleDTO.RoleResponse;
import com.flowright.member_service.dto.RoleDTO.UpdateRoleRequest;
import com.flowright.member_service.entity.Role;
import com.flowright.member_service.kafka.producer.CreateNotificationProducer;
import com.flowright.member_service.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {
    @Autowired
    private final RoleRepository roleRepository;

    @Autowired
    private final CreateNotificationProducer createNotificationProducer;

    @Transactional
    public RoleResponse createRole(CreateRoleRequest request, UUID workspaceId) {
        if (roleRepository.existsByNameAndWorkspaceId(request.getName(), workspaceId)) {
            throw new RuntimeException("Role already exists");
        }

        Role role = Role.builder()
                .name(request.getName())
                .description(request.getDescription())
                .workspaceId(workspaceId)
                .build();

        Role savedRole = roleRepository.save(role);
        createNotificationProducer.sendMessage(
                workspaceId,
                savedRole.getId(),
                "Role Created",
                "Role has been created",
                "/role/" + savedRole.getId(),
                "role");
        return toRoleResponse(savedRole);
    }

    public UUID createFirstAdminRole(UUID workspaceId, String name, String description) {
        if (roleRepository.existsByNameAndWorkspaceId(name, workspaceId)) {
            throw new RuntimeException("Role already exists");
        }

        Role role = Role.builder()
                .name(name)
                .description(description)
                .workspaceId(workspaceId)
                .build();
        Role savedRole = roleRepository.save(role);
        return savedRole.getId();
    }

    public void createFirstGuestRole(UUID workspaceId, String name, String description) {
        if (roleRepository.existsByNameAndWorkspaceId(name, workspaceId)) {
            throw new RuntimeException("Role already exists");
        }

        Role role = Role.builder()
                .name(name)
                .description(description)
                .workspaceId(workspaceId)
                .build();
        roleRepository.save(role);
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

    public List<RoleResponse> getAllRolesByWorkspaceId(UUID workspaceId) {
        return roleRepository.findByWorkspaceId(workspaceId).stream()
                .map(this::toRoleResponse)
                .collect(Collectors.toList());
    }

    public String getRoleNameById(UUID id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found"));
        return role.getName();
    }
}
