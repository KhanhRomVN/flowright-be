package com.flowright.member_service.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flowright.member_service.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    List<Role> findByWorkspaceId(UUID workspaceId);

    Optional<Role> findByWorkspaceIdAndName(UUID workspaceId, String name);

    Page<Role> findByWorkspaceId(UUID workspaceId, Pageable pageable);
}
