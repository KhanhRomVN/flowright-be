package com.flowright.member_service.controller;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flowright.member_service.dto.RoleDTO.CreateRoleRequest;
import com.flowright.member_service.dto.RoleDTO.RoleResponse;
import com.flowright.member_service.dto.RoleDTO.UpdateRoleRequest;
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
    @PostMapping
    public ResponseEntity<RoleResponse> createRole(
            @Valid @RequestBody CreateRoleRequest request, @RequestHeader("access_token") String token) {
        jwtService.validateToken(token);
        return ResponseEntity.ok(roleService.createRole(request));
    }

    // update role: /member-service/roles/{id}
    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> updateRole(
            @PathVariable Long id,
            @RequestBody UpdateRoleRequest request,
            @RequestHeader("access_token") String token) {
        jwtService.validateToken(token);
        return ResponseEntity.ok(roleService.updateRole(id, request));
    }

    // delete role: /member-service/roles/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id, @RequestHeader("access_token") String token) {
        jwtService.validateToken(token);
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }

    // get role by id: /member-service/roles/{id}
    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> getRoleById(
            @PathVariable Long id, @RequestHeader("access_token") String token) {
        jwtService.validateToken(token);
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    // get admin role by workspace id: /member-service/roles/admin/{workspaceId}
    @GetMapping("/admin/{workspaceId}")
    public ResponseEntity<RoleResponse> getAdminRoleByWorkspaceId(
            @PathVariable Long workspaceId, @RequestHeader("access_token") String token) {
        jwtService.validateToken(token);
        return ResponseEntity.ok(roleService.getAdminRoleByWorkspaceId(workspaceId));
    }

    // get all roles: /member-service/workspace/roles
    @GetMapping("/workspace/roles")
    public ResponseEntity<Page<RoleResponse>> getAllRolesByWorkspaceId(@RequestHeader("access_token") String token) {
        Long workspaceId = jwtService.extractWorkspaceId(token);
        System.out.println(workspaceId);
        return ResponseEntity.ok(roleService.getAllRolesByWorkspaceId(workspaceId));
    }
}
