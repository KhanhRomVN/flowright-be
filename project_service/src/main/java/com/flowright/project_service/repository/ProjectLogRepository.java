package com.flowright.project_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flowright.project_service.entity.ProjectLog;

public interface ProjectLogRepository extends JpaRepository<ProjectLog, UUID> {}
