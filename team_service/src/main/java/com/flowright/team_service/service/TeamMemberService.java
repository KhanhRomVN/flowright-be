package com.flowright.team_service.service;

import org.springframework.stereotype.Service;

import com.flowright.team_service.entity.TeamMember;
import com.flowright.team_service.repository.TeamMemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamMemberService {
    private final TeamMemberRepository teamMemberRepository;

    public Integer getNumberOfMembersInTeam(Long teamId, Long workspaceId) {
        return teamMemberRepository.countByTeamIdAndWorkspaceId(teamId, workspaceId);
    }

    public void addMemberToTeam(Long teamId, Long userId, Long workspaceId) {
        TeamMember teamMember = TeamMember.builder()
                .teamId(teamId)
                .userId(userId)
                .workspaceId(workspaceId)
                .build();
        teamMemberRepository.save(teamMember);
    }
}
