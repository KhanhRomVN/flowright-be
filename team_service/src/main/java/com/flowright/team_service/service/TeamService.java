package com.flowright.team_service.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flowright.team_service.dto.TeamDTO.CreateTeamRequest;
import com.flowright.team_service.dto.TeamDTO.CreateTeamResponse;
import com.flowright.team_service.entity.Team;
import com.flowright.team_service.repository.TeamRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;

    @Transactional
    public CreateTeamResponse createTeam(CreateTeamRequest request, Long workspaceId) {
        Team team = Team.builder()
                .name(request.getName())
                .description(request.getDescription())
                .type(request.getType())
                .status("active") // Default status
                .leaderId(request.getLeaderId())
                .workspaceId(workspaceId)
                .build();

        Team savedTeam = teamRepository.save(team);
        return CreateTeamResponse.builder()
                .name(savedTeam.getName())
                .description(savedTeam.getDescription())
                .type(savedTeam.getType())
                .status(savedTeam.getStatus())
                .leaderId(savedTeam.getLeaderId())
                .build();
    }
}
