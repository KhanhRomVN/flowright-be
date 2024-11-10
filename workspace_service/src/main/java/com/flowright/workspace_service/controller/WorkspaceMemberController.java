package com.flowright.workspace_service.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flowright.workspace_service.dto.WorkspaceMemberDTO.GetListWorkspaceMemberReponse;
import com.flowright.workspace_service.service.JwtService;
import com.flowright.workspace_service.service.WorkspaceMemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/workspace-service/workspace-members")
@RequiredArgsConstructor
public class WorkspaceMemberController {
    private final WorkspaceMemberService workspaceMemberService;
    private final JwtService jwtService;

    // Get list members workspace by user id: /workspace-service/workspace-members
    @GetMapping
    public ResponseEntity<List<GetListWorkspaceMemberReponse>> getListMembersWorkspaceByUserId(@RequestHeader("access_token") String token) {
        UUID userId = jwtService.extractUserId(token);
        return ResponseEntity.ok(workspaceMemberService.getListMembersWorkspaceByUserId(userId));
    }
}
