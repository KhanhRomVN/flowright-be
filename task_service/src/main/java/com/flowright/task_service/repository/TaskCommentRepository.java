package com.flowright.task_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flowright.task_service.entity.TaskComment;

public interface TaskCommentRepository extends JpaRepository<TaskComment, UUID> {}
