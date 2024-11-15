package com.flowright.workspace_service.controller;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flowright.workspace_service.dto.InviteDTO.AcceptInviteReponse;
import com.flowright.workspace_service.dto.InviteDTO.AcceptInviteRequest;
import com.flowright.workspace_service.dto.InviteDTO.CreateInviteRequest;
import com.flowright.workspace_service.dto.InviteDTO.CreateInviteResponse;
import com.flowright.workspace_service.dto.InviteDTO.GetInviteResponse;
import com.flowright.workspace_service.service.InviteService;
import com.flowright.workspace_service.service.JwtService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/workspace/invites")
@RequiredArgsConstructor
public class InviteController {
    private final InviteService inviteService;
    private final JwtService jwtService;

    // create invite: /workspace/invites
    @PostMapping
    public ResponseEntity<CreateInviteResponse> createInvite(
            @RequestHeader("access_token") String token, @Valid @RequestBody CreateInviteRequest request) {
        UUID workspaceId = jwtService.extractWorkspaceId(token);
        return ResponseEntity.ok(inviteService.createInvite(workspaceId, request));
    }

    // accept invite: /workspace/invites/accept
    @PostMapping("/accept")
    public ResponseEntity<AcceptInviteReponse> acceptInvite(
            @Valid @RequestBody AcceptInviteRequest request, @RequestHeader("access_token") String token) {
        UUID userId = jwtService.extractUserId(token);
        return ResponseEntity.ok(inviteService.acceptInvite(request, userId));
    }

    // get list invite: /workspace/invites
    @GetMapping
    public ResponseEntity<List<GetInviteResponse>> getListInvite(@RequestHeader("access_token") String token) {
        UUID workspaceId = jwtService.extractWorkspaceId(token);
        return ResponseEntity.ok(inviteService.getListInvite(workspaceId));
    }

    // delete invite: /workspace/invites
    @DeleteMapping
    public ResponseEntity<String> deleteInvite(
            @RequestParam("id") String id, @RequestHeader("access_token") String token) {
        jwtService.validateToken(token);
        inviteService.deleteInvite(UUID.fromString(id));
        return ResponseEntity.ok("Invite deleted successfully");
    }
}
