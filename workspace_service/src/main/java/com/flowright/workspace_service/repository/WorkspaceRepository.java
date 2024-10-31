package com.flowright.workspace_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flowright.workspace_service.entity.Workspace;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
    List<Workspace> findByOwnerId(Long ownerId);
}
