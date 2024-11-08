package com.flowright.member_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flowright.member_service.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, UUID> {
    boolean existsByName(String name);
}
