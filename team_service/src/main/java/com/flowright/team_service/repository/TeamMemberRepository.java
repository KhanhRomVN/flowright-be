package com.flowright.team_service.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flowright.team_service.entity.TeamMember;

public interface TeamMemberRepository extends JpaRepository<TeamMember, UUID> {
    List<TeamMember> findByMemberId(UUID memberId);

    List<TeamMember> findByTeamId(UUID teamId);
}
