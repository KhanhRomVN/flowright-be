package com.flowright.team_service.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.flowright.team_service.entity.TeamMember;
import com.flowright.team_service.repository.TeamMemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamMemberService {
    private final TeamMemberRepository teamMemberRepository;

    public String addMemberToTeam(UUID teamId, UUID memberId) {
        TeamMember teamMember =
                TeamMember.builder().teamId(teamId).memberId(memberId).build();
        teamMemberRepository.save(teamMember);
        return "Member added to team successfully";
    }
}
