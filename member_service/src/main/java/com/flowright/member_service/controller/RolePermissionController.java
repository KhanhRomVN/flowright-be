package com.flowright.member_service.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.flowright.member_service.dto.AssignPermissionRequest;
import com.flowright.member_service.dto.PermissionResponse;
import com.flowright.member_service.service.JwtService;
import com.flowright.member_service.service.RolePermissionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/member-service/role-permissions")
@RequiredArgsConstructor
public class RolePermissionController {
    private final RolePermissionService rolePermissionService;
    private final JwtService jwtService;

    @PostMapping("/roles/{roleId}/permissions")
    public ResponseEntity<Void> assignPermissionToRole(
            @PathVariable Long roleId,
            @Valid @RequestBody AssignPermissionRequest request,
            @RequestHeader("access_token") String token) {
        Long userId = jwtService.extractUserId(token);
        rolePermissionService.assignPermissionToRole(roleId, request.getPermissionId());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/roles/{roleId}/permissions/{permissionId}")
    public ResponseEntity<Void> removePermissionFromRole(
            @PathVariable Long roleId, @PathVariable Long permissionId, @RequestHeader("access_token") String token) {
        Long userId = jwtService.extractUserId(token);
        rolePermissionService.removePermissionFromRole(roleId, permissionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/roles/{roleId}/permissions")
    public ResponseEntity<List<PermissionResponse>> getRolePermissions(
            @PathVariable Long roleId, @RequestHeader("access_token") String token) {
        Long userId = jwtService.extractUserId(token);
        return ResponseEntity.ok(rolePermissionService.getRolePermissions(roleId));
    }
}
