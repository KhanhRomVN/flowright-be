package com.flowright.team_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flowright.team_service.entity.TeamMember;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    Integer countByTeamIdAndWorkspaceId(Long teamId, Long workspaceId);
}
