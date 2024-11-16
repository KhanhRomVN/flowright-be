package com.flowright.task_service.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flowright.task_service.entity.MiniTask;

@Repository
public interface MiniTaskRepository extends JpaRepository<MiniTask, UUID> {
    List<MiniTask> findByTaskId(UUID taskId);
}
