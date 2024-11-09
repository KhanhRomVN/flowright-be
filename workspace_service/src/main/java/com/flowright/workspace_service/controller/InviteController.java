package com.flowright.workspace_service.controller;

import jakarta.validation.Valid;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.flowright.workspace_service.dto.InviteDTO.CreateInviteRequest;
import com.flowright.workspace_service.dto.InviteDTO.CreateInviteResponse;
import com.flowright.workspace_service.dto.InviteDTO.AcceptInviteRequest;
import com.flowright.workspace_service.dto.InviteDTO.AcceptInviteReponse;
import com.flowright.workspace_service.service.InviteService;
import com.flowright.workspace_service.service.JwtService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/workspace-service/invites")
@RequiredArgsConstructor
public class InviteController {
    private final InviteService inviteService;
    private final JwtService jwtService;    

    // create invite: /workspace-service/invites
    @PostMapping
    public ResponseEntity<CreateInviteResponse> createInvite(@RequestHeader("access_token") String token, @Valid @RequestBody CreateInviteRequest request) {
        UUID workspaceId = jwtService.extractWorkspaceId(token);
        return ResponseEntity.ok(inviteService.createInvite(workspaceId, request));
    }

    // accept invite: /workspace-service/invites/accept
    @PostMapping("/accept")
    public ResponseEntity<AcceptInviteReponse> acceptInvite(@Valid @RequestBody AcceptInviteRequest request, @RequestHeader("access_token") String token) {
        UUID userId = jwtService.extractUserId(token);
        return ResponseEntity.ok(inviteService.acceptInvite(request, userId));
    }

}
