package com.flowright.workspace_service.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.flowright.workspace_service.dto.WorkspaceDTO;
import com.flowright.workspace_service.service.WorkspaceService;
import com.flowright.workspace_service.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/workspaces")
@RequiredArgsConstructor
public class WorkspaceController {
    private final WorkspaceService workspaceService;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<WorkspaceDTO> createWorkspace(
            @Valid @RequestBody WorkspaceDTO workspaceDTO, @RequestHeader("Authorization") String token) {
        Long userId = jwtUtil.getUserIdFromToken(token.substring(7));
        workspaceDTO.setOwnerId(userId);
        return ResponseEntity.ok(workspaceService.createWorkspace(workspaceDTO));
    }

    @GetMapping
    public ResponseEntity<List<WorkspaceDTO>> getWorkspaces(@RequestHeader("Authorization") String token) {
        Long userId = jwtUtil.getUserIdFromToken(token.substring(7));
        return ResponseEntity.ok(workspaceService.getWorkspacesByOwnerId(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkspaceDTO> updateWorkspace(
            @PathVariable Long id,
            @Valid @RequestBody WorkspaceDTO workspaceDTO,
            @RequestHeader("Authorization") String token) {
        Long userId = jwtUtil.getUserIdFromToken(token.substring(7));
        return ResponseEntity.ok(workspaceService.updateWorkspace(id, workspaceDTO, userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkspace(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        Long userId = jwtUtil.getUserIdFromToken(token.substring(7));
        workspaceService.deleteWorkspace(id, userId);
        return ResponseEntity.noContent().build();
    }
}
