package com.flowright.team_service.controller;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flowright.team_service.dto.TeamDTO.CreateTeamRequest;
import com.flowright.team_service.dto.TeamDTO.CreateTeamResponse;
import com.flowright.team_service.service.JwtService;
import com.flowright.team_service.service.TeamService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/team-service/teams")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;
    private final JwtService jwtService;

    // create team: /team-service/teams
    @PostMapping
    public ResponseEntity<CreateTeamResponse> createTeam(
            @Valid @RequestBody CreateTeamRequest request, @RequestHeader("access_token") String token) {
        Long workspaceId = jwtService.extractWorkspaceId(token);
        CreateTeamResponse response = teamService.createTeam(request, workspaceId);
        return ResponseEntity.ok(response);
    }
}
