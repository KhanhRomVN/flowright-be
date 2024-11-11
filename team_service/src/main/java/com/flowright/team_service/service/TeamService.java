package com.flowright.team_service.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flowright.team_service.dto.TeamDTO.CreateTeamRequest;
import com.flowright.team_service.entity.Team;
import com.flowright.team_service.repository.TeamRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;

    @Autowired
    private final TeamMemberService teamMemberService;

    @Transactional
    public String createTeam(CreateTeamRequest request, UUID workspaceId) {
        Team team = Team.builder()
                .leaderId(UUID.fromString(request.getLeaderId()))
                .name(request.getName())
                .description(request.getDescription())
                .type(request.getType())
                .status("active")
                .workspaceId(workspaceId)
                .build();

        Team savedTeam = teamRepository.save(team);
        teamMemberService.addMemberToTeam(savedTeam.getId(), UUID.fromString(request.getLeaderId()));
        return "Team created successfully";
    }

    public List<Team> getAllTeamWorkspace(UUID workspaceId) {
        return teamRepository.findByWorkspaceId(workspaceId);
    }
}
