package com.flowright.member_service.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flowright.member_service.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, UUID> {
    List<Member> findByWorkspaceId(UUID workspaceId);

    Optional<Member> findByUserIdAndWorkspaceId(UUID userId, UUID workspaceId);

    boolean existsByUserIdAndWorkspaceId(UUID userId, UUID workspaceId);

    List<Member> findByRoleId(UUID roleId);

    int countByWorkspaceId(UUID workspaceId);
}
