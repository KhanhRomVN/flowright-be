package com.flowright.member_service.controller;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.flowright.member_service.dto.CreateRoleRequest;
import com.flowright.member_service.dto.RoleResponse;
import com.flowright.member_service.dto.UpdateRoleRequest;
import com.flowright.member_service.service.JwtService;
import com.flowright.member_service.service.RoleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/member-service/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<RoleResponse> createRole(
            @Valid @RequestBody CreateRoleRequest request, @RequestHeader("access_token") String token) {
        Long userId = jwtService.extractUserId(token);
        return ResponseEntity.ok(roleService.createRole(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> updateRole(
            @PathVariable Long id,
            @RequestBody UpdateRoleRequest request,
            @RequestHeader("access_token") String token) {
        Long userId = jwtService.extractUserId(token);
        return ResponseEntity.ok(roleService.updateRole(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id, @RequestHeader("access_token") String token) {
        Long userId = jwtService.extractUserId(token);
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> getRoleById(
            @PathVariable Long id, @RequestHeader("access_token") String token) {
        Long userId = jwtService.extractUserId(token);
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    @GetMapping
    public ResponseEntity<Page<RoleResponse>> getAllRoles(
            Pageable pageable, @RequestHeader("access_token") String token) {
        Long userId = jwtService.extractUserId(token);
        return ResponseEntity.ok(roleService.getAllRoles(pageable));
    }
}
