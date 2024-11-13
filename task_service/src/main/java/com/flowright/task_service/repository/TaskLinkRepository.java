package com.flowright.task_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flowright.task_service.entity.TaskLink;

public interface TaskLinkRepository extends JpaRepository<TaskLink, UUID> {}
