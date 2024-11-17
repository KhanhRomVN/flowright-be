package com.flowright.project_service.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flowright.project_service.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
    List<Project> findByWorkspaceId(UUID workspaceId);

    List<Project> findByOwnerId(UUID ownerId);
}
