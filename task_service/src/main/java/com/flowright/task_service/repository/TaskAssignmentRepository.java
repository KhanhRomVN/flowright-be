package com.flowright.task_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flowright.task_service.entity.TaskAssignment;

public interface TaskAssignmentRepository extends JpaRepository<TaskAssignment, UUID> {}
