package com.flowright.member_service.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flowright.member_service.dto.RoleDTO.RoleResponse;
import com.flowright.member_service.service.JwtService;
import com.flowright.member_service.service.RoleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/member-service/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;
    private final JwtService jwtService;

    // create role: /member-service/roles
    // @PostMapping
    // public ResponseEntity<RoleResponse> createRole(
    //         @Valid @RequestBody CreateRoleRequest request, @RequestHeader("access_token") String token) {
    //     String workspaceId = jwtService.extractWorkspaceId(token);
    //     return ResponseEntity.ok(roleService.createRole(request, workspaceId));
    // }

    // get role by id: /member-service/roles/{id}
    // @GetMapping("/{id}")
    // public ResponseEntity<RoleResponse> getRoleById(
    //         @PathVariable UUID id, @RequestHeader("access_token") String token) {
    //     jwtService.validateToken(token);
    //     return ResponseEntity.ok(roleService.getRoleById(id));
    // }

    // get admin role by workspace id: /member-service/roles/admin/{workspaceId}
    // @GetMapping("/admin/{workspaceId}")
    // public ResponseEntity<RoleResponse> getAdminRoleByWorkspaceId(
    //         @PathVariable UUID workspaceId, @RequestHeader("access_token") String token) {
    //     jwtService.validateToken(token);
    //     return ResponseEntity.ok(roleService.getAdminRoleByWorkspaceId(workspaceId));
    // }

    // get all roles: /member-service/workspace/roles
    @GetMapping("/workspace/roles")
    public ResponseEntity<Page<RoleResponse>> getAllRolesByWorkspaceId(@RequestHeader("access_token") String token) {
        UUID workspaceId = jwtService.extractWorkspaceId(token);
        return ResponseEntity.ok(roleService.getAllRolesByWorkspaceId(workspaceId));
    }
}
