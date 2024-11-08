package com.flowright.team_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flowright.team_service.entity.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findByWorkspaceId(Long workspaceId);
}
