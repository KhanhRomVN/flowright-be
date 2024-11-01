package com.flowright.workspace_service.controller;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.flowright.workspace_service.dto.InviteDTO;
import com.flowright.workspace_service.service.InviteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/workspaces/{workspaceId}/invites")
@RequiredArgsConstructor
public class InviteController {
    private final InviteService inviteService;
    // private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<InviteDTO> createInvite(
            @PathVariable Long workspaceId,
            @Valid @RequestBody InviteDTO inviteDTO,
            @RequestHeader("access_token") String token) {
        inviteDTO.setWorkspaceId(workspaceId);
        return ResponseEntity.ok(inviteService.createInvite(inviteDTO));
    }

    @PostMapping("/verify")
    public ResponseEntity<InviteDTO> verifyInvite(@RequestParam String email, @RequestParam String otp) {
        return ResponseEntity.ok(inviteService.verifyInvite(email, otp));
    }

    // @GetMapping
    // public ResponseEntity<List<InviteDTO>> getWorkspaceInvites(
    //         @PathVariable Long workspaceId, @RequestHeader("access_token") String token) {
    //     return ResponseEntity.ok(inviteService.getInvitesByWorkspaceId(workspaceId));
    // }
}
