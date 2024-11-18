package com.flowright.team_service.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flowright.team_service.dto.TeamMemberDTO.AddMemberRequest;
import com.flowright.team_service.dto.TeamMemberDTO.GetListMemberTeamResponse;
import com.flowright.team_service.service.JwtService;
import com.flowright.team_service.service.TeamMemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/team/service/teams/members")
@RequiredArgsConstructor
public class TeamMemberController {
    private final TeamMemberService teamMemberService;
    private final JwtService jwtService;

    // add member to team: /team/service/teams/members
    @PostMapping
    public ResponseEntity<String> addMemberToTeam(
            @RequestBody AddMemberRequest request, @RequestHeader("access_token") String token) {
        jwtService.validateToken(token);
        return ResponseEntity.ok(teamMemberService.addMemberToTeam(
                UUID.fromString(request.getTeamId()), UUID.fromString(request.getMemberId())));
    }

    // get all member in team: /team/service/teams/members?teamId=<teamId>
    @GetMapping
    public ResponseEntity<List<GetListMemberTeamResponse>> getAllMemberInTeam(
            @RequestParam String teamId, @RequestHeader("access_token") String token) {
        jwtService.validateToken(token);
        return ResponseEntity.ok(teamMemberService.getAllMemberInTeam(UUID.fromString(teamId)));
    }

    //
}
