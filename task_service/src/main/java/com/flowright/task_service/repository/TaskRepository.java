package com.flowright.task_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flowright.task_service.entity.Task;

public interface TaskRepository extends JpaRepository<Task, UUID> {}
