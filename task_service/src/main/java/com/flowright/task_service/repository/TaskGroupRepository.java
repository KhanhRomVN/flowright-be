package com.flowright.task_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flowright.task_service.entity.TaskGroup;

public interface TaskGroupRepository extends JpaRepository<TaskGroup, UUID> {}
