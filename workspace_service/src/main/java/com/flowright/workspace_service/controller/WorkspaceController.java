package com.flowright.workspace_service.controller;

import java.util.List;

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

import com.flowright.workspace_service.dto.WorkspaceDTO;
import com.flowright.workspace_service.service.WorkspaceService;
import com.flowright.workspace_service.util.JwtService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/workspace-service/workspaces")
@RequiredArgsConstructor
public class WorkspaceController {
    private final WorkspaceService workspaceService;
    private final JwtService jwtService;

    // create workspace: /workspace-service/workspaces
    @PostMapping
    public ResponseEntity<WorkspaceDTO> createWorkspace(
            @Valid @RequestBody WorkspaceDTO workspaceDTO, @RequestHeader("access_token") String token) {
        Long userId = jwtService.extractUserId(token);
        workspaceDTO.setOwnerId(userId);
        return ResponseEntity.ok(workspaceService.createWorkspace(workspaceDTO, token));
    }

    // get workspaces
    @GetMapping
    public ResponseEntity<List<WorkspaceDTO>> getWorkspaces(@RequestHeader("access_token") String token) {
        Long userId = jwtService.extractUserId(token);
        return ResponseEntity.ok(workspaceService.getWorkspacesByOwnerId(userId));
    }

    // update workspace
    @PutMapping("/{id}")
    public ResponseEntity<WorkspaceDTO> updateWorkspace(
            @PathVariable Long id,
            @Valid @RequestBody WorkspaceDTO workspaceDTO,
            @RequestHeader("access_token") String token) {
        Long userId = jwtService.extractUserId(token);
        return ResponseEntity.ok(workspaceService.updateWorkspace(id, workspaceDTO, userId));
    }

    // delete workspace
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkspace(@PathVariable Long id, @RequestHeader("access_token") String token) {
        Long userId = jwtService.extractUserId(token);
        workspaceService.deleteWorkspace(id, userId);
        return ResponseEntity.noContent().build();
    }
}
