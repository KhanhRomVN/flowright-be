package com.flowright.team_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flowright.team_service.service.JwtService;
import com.flowright.team_service.service.TeamMemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/team-service/teams/members")
@RequiredArgsConstructor
public class TeamMemberController {
    private final TeamMemberService teamMemberService;
    private final JwtService jwtService;

    // get number of members in a team: /team-service/teams/members/{team_id}
    @GetMapping("/members/{team_id}")
    public ResponseEntity<Integer> getNumberOfMembersInTeam(
            @PathVariable Long team_id, @RequestHeader("access_token") String token) {
        Long workspaceId = jwtService.extractWorkspaceId(token);
        return ResponseEntity.ok(teamMemberService.getNumberOfMembersInTeam(team_id, workspaceId));
    }
}
