package com.flowright.team_service.controller;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flowright.team_service.dto.TeamDTO.CreateTeamRequest;
import com.flowright.team_service.dto.TeamDTO.GetTeamOfWorkspaceResponse;
import com.flowright.team_service.entity.Team;
import com.flowright.team_service.service.JwtService;
import com.flowright.team_service.service.TeamService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/team/service/teams")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;
    private final JwtService jwtService;

    // create team: /team/service/teams
    @PostMapping
    public ResponseEntity<String> createTeam(
            @Valid @RequestBody CreateTeamRequest request, @RequestHeader("access_token") String token) {
        UUID workspaceId = jwtService.extractWorkspaceId(token);
        return ResponseEntity.ok(teamService.createTeam(request, workspaceId));
    }

    // get all team in workspace: /team/service/teams
    @GetMapping
    public ResponseEntity<List<GetTeamOfWorkspaceResponse>> getAllTeamWorkspace(
            @RequestHeader("access_token") String token) {
        UUID workspaceId = jwtService.extractWorkspaceId(token);
        return ResponseEntity.ok(teamService.getAllTeamWorkspace(workspaceId));
    }

    // get all team in workspace that user is member of: /team/service/teams/member
    @GetMapping("/member")
    public ResponseEntity<List<Team>> getMemberTeamWorkspace(@RequestHeader("access_token") String token) {
        UUID memberId = jwtService.extractMemberId(token);
        return ResponseEntity.ok(teamService.getMemberTeamWorkspace(memberId));
    }
}
