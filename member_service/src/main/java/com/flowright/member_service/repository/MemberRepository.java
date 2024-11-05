package com.flowright.member_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flowright.member_service.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByWorkspaceId(Long workspaceId);

    Optional<Member> findByUserIdAndWorkspaceId(Long userId, Long workspaceId);

    boolean existsByUserIdAndWorkspaceId(Long userId, Long workspaceId);

    List<Member> findByRoleId(Long roleId);
}
