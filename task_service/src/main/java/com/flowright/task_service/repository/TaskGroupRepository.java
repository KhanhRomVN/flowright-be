package com.flowright.task_service.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flowright.task_service.entity.TaskGroup;

public interface TaskGroupRepository extends JpaRepository<TaskGroup, UUID> {
    boolean existsByNameAndProjectId(String name, UUID projectId);

    List<TaskGroup> findByProjectId(UUID projectId);
}
