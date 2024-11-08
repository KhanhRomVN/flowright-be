package com.flowright.workspace_service.controller;

import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flowright.workspace_service.dto.WorkspaceDTO.CreateWorkspaceRequest;
import com.flowright.workspace_service.dto.WorkspaceDTO.CreateWorkspaceReponse;
import com.flowright.workspace_service.service.JwtService;
import com.flowright.workspace_service.service.WorkspaceService;

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
    public ResponseEntity<CreateWorkspaceReponse> createWorkspace(
            @Valid @RequestBody CreateWorkspaceRequest requestBody, @RequestHeader("access_token") String token) {
        UUID ownerId = jwtService.extractUserId(token);
        return ResponseEntity.ok(workspaceService.createWorkspace(requestBody, ownerId));
    }
    
    // get workspaces
    // @GetMapping
    // public ResponseEntity<List<WorkspaceDTO>> getWorkspaces(@RequestHeader("access_token") String token) {
    //     UUID userId = jwtService.extractUserId(token);
    //     return ResponseEntity.ok(workspaceService.getWorkspacesByOwnerId(userId));
    // }
}
