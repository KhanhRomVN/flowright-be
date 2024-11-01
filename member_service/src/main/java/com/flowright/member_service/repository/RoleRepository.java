package com.flowright.member_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flowright.member_service.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findByWorkspaceId(Long workspaceId);

    Optional<Role> findByWorkspaceIdAndIsDefaultTrue(Long workspaceId);

    Optional<Role> findByWorkspaceIdAndNameAndIsDefault(Long workspaceId, String name, boolean isDefault);
}
