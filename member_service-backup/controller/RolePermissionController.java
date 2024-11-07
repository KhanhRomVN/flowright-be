package com.flowright.member_service.controller;

import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flowright.member_service.dto.RolePermissionDTO.AssignPermissionRequest;
import com.flowright.member_service.service.JwtService;
import com.flowright.member_service.service.RolePermissionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/member-service/role-permissions")
@RequiredArgsConstructor
public class RolePermissionController {
    private final RolePermissionService rolePermissionService;
    private final JwtService jwtService;

    // assign permission to role: /member-service/role-permissions/roles/{roleId}/permissions
    @PostMapping("/roles/{roleId}/permissions")
    public ResponseEntity<Void> assignPermissionToRole(
            @PathVariable UUID roleId,
            @Valid @RequestBody AssignPermissionRequest request,
            @RequestHeader("access_token") String token) {
        jwtService.validateToken(token);
        try {
            rolePermissionService.assignPermissionToRole(roleId, request.getPermissionId());
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // get role permissions: /member-service/role-permissions/roles/{roleId}/permissions
    // @GetMapping("/roles/{roleId}/permissions")
    // public ResponseEntity<List<PermissionResponse>> getRolePermissions(
    //         @PathVariable UUID roleId, @RequestHeader("access_token") String token) {
    //     jwtService.validateToken(token);
    //     try {
    //         return ResponseEntity.ok(rolePermissionService.getRolePermissions(roleId));
    //     } catch (RuntimeException e) {
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    //     }
    // }
}
