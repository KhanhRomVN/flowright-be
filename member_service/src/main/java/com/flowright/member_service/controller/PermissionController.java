package com.flowright.member_service.controller;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.flowright.member_service.dto.CreatePermissionRequest;
import com.flowright.member_service.dto.PermissionResponse;
import com.flowright.member_service.service.PermissionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/member-service/permissions")
@RequiredArgsConstructor
public class PermissionController {
    private final PermissionService permissionService;

    // Create a new permission: POST /permissions
    @PostMapping
    public ResponseEntity<PermissionResponse> createPermission(@Valid @RequestBody CreatePermissionRequest request) {
        System.out.println("Creating permission: " + request);
        return ResponseEntity.ok(permissionService.createPermission(request));
    }

    // Update a permission
    // @PutMapping("/{id}")
    // public ResponseEntity<PermissionResponse> updatePermission(
    //         @PathVariable Long id, @RequestBody UpdatePermissionRequest request) {
    //     return ResponseEntity.ok(permissionService.updatePermission(id, request));
    // }

    // Delete a permission
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
        permissionService.deletePermission(id);
        return ResponseEntity.noContent().build();
    }

    // Get a permission by ID
    @GetMapping("/{id}")
    public ResponseEntity<PermissionResponse> getPermissionById(@PathVariable Long id) {
        return ResponseEntity.ok(permissionService.getPermissionById(id));
    }

    // Get all permissions
    @GetMapping
    public ResponseEntity<Page<PermissionResponse>> getAllPermissions(Pageable pageable) {
        return ResponseEntity.ok(permissionService.getAllPermissions(pageable));
    }
}
