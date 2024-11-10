package com.flowright.workspace_service.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flowright.workspace_service.entity.WorkspaceMember;

@Repository
public interface WorkspaceMemberRepository extends JpaRepository<WorkspaceMember, UUID> {
    List<WorkspaceMember> findByUserId(UUID userId);
    WorkspaceMember findByUserIdAndWorkspaceId(UUID userId, UUID workspaceId);
    List<WorkspaceMember> findByWorkspaceId(UUID workspaceId);
    
}
