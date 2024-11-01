package com.flowright.workspace_service.controller;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.flowright.workspace_service.dto.InviteDTO;
import com.flowright.workspace_service.service.InviteService;
import com.flowright.workspace_service.util.JwtService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/workspace-service/invites")
@RequiredArgsConstructor
public class InviteController {
    private final InviteService inviteService;
    private final JwtService jwtService;

    // create invite: /workspace-service/invites
    @PostMapping
    public ResponseEntity<InviteDTO> createInvite(
            @Valid @RequestBody InviteDTO inviteDTO,
            @RequestHeader("access_token") String token) {
        jwtService.extractUserId(token);
        return ResponseEntity.ok(inviteService.createInvite(inviteDTO));
    }

    // verify invite: /workspace-service/invites/verify
    @PostMapping("/verify")
    public ResponseEntity<InviteDTO> verifyInvite(@RequestParam String email, @RequestParam String otp) {
        return ResponseEntity.ok(inviteService.verifyInvite(email, otp));
    }

    // get workspace invites: /workspace-service/invites
}
