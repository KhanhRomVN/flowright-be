package com.flowright.workspace_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import com.flowright.workspace_service.dto.WorkspaceMemberDTO.GetListWorkspaceMemberReponse;
import com.flowright.workspace_service.service.WorkspaceMemberService;
import com.flowright.workspace_service.service.JwtService;
@RestController
@RequestMapping("/workspace-service/workspace-members")
public class WorkspaceMemberController {
    private WorkspaceMemberService workspaceMemberService;
    private JwtService jwtService;

    // Get list members workspace by user id: /workspace-service/workspace-members/list-members
    @GetMapping
    public ResponseEntity<List<GetListWorkspaceMemberReponse>> getListMembersWorkspaceByUserId(@RequestHeader("access_token") String token) {
        UUID userId = jwtService.extractUserId(token);
        return ResponseEntity.ok(workspaceMemberService.getListMembersWorkspaceByUserId(userId));
    }
}
