package com.flowright.task_service.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flowright.task_service.entity.TaskAssignment;

public interface TaskAssignmentRepository extends JpaRepository<TaskAssignment, UUID> {
    List<TaskAssignment> findAllByTeamId(UUID teamId);

    List<TaskAssignment> findAllByTaskId(UUID taskId);

    List<TaskAssignment> findAllByMemberId(UUID memberId);
}
