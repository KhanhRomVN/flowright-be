package com.flowright.member_service.controller;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// DTO
import com.flowright.member_service.dto.PermissionDTO.CreatePermissionRequest;
import com.flowright.member_service.dto.PermissionDTO.PermissionResponse;

// Service
import com.flowright.member_service.service.PermissionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/member-service/permissions")
@RequiredArgsConstructor
public class PermissionController {
    private final PermissionService permissionService;

    // Create a new permission: POST /member-service/permissions
    @PostMapping
    public ResponseEntity<PermissionResponse> createPermission(@Valid @RequestBody CreatePermissionRequest request) {
        System.out.println("Creating permission: " + request);
        return ResponseEntity.ok(permissionService.createPermission(request));
    }

    // Delete a permission: DELETE /member-service/permissions/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
        permissionService.deletePermission(id);
        return ResponseEntity.noContent().build();
    }

    // Get all permissions: GET /member-service/permissions
    @GetMapping
    public ResponseEntity<Page<PermissionResponse>> getAllPermissions(Pageable pageable) {
        return ResponseEntity.ok(permissionService.getAllPermissions(pageable));
    }
}
