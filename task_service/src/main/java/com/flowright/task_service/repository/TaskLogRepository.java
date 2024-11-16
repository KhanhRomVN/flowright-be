package com.flowright.task_service.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flowright.task_service.entity.TaskLog;

public interface TaskLogRepository extends JpaRepository<TaskLog, UUID> {
    List<TaskLog> findByTaskId(UUID taskId);
}
