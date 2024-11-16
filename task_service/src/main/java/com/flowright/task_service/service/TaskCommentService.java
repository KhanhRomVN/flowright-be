package com.flowright.task_service.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.flowright.task_service.entity.TaskComment;
import com.flowright.task_service.repository.TaskCommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskCommentService {
    private final TaskCommentRepository taskCommentRepository;

    public List<TaskComment> getAllTaskCommentsByTaskId(UUID taskId) {
        return taskCommentRepository.findByTaskId(taskId);
    }
}
