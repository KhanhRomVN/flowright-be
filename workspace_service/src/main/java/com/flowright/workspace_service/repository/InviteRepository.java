package com.flowright.workspace_service.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flowright.workspace_service.entity.Invite;

@Repository
public interface InviteRepository extends JpaRepository<Invite, UUID> {
    Optional<Invite> findByEmailAndToken(String email, String token);
    Invite findByTokenAndWorkspaceIdAndEmail(String token, UUID workspaceId, String email);
    void deleteInviteByWorkspaceIdAndEmail(UUID workspaceId, String email);
    void deleteInviteByEmailAndToken(String email, String token);
    void deleteInviteById(UUID id);
    Invite findByWorkspaceIdAndEmail(UUID workspaceId, String email);
    List<Invite> findByWorkspaceId(UUID workspaceId);
    List<Invite> findByStatusAndExpiresAtBefore(String status, LocalDateTime expiresAt);
}
