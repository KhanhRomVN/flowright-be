package com.flowright.task_service.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flowright.task_service.dto.TaskCommentDTO.CreateTaskCommentRequest;
import com.flowright.task_service.dto.TaskCommentDTO.CreateTaskCommentResponse;
import com.flowright.task_service.entity.TaskComment;
import com.flowright.task_service.repository.TaskCommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskCommentService {
    @Autowired
    private final TaskCommentRepository taskCommentRepository;

    @Autowired
    private final TaskLogService taskLogService;

    public List<TaskComment> getAllTaskCommentsByTaskId(UUID taskId) {
        return taskCommentRepository.findByTaskId(taskId);
    }

    public CreateTaskCommentResponse createTaskComment(CreateTaskCommentRequest request, UUID taskId, UUID memberId) {
        taskCommentRepository.save(TaskComment.builder()
                .comment(request.getComment())
                .taskId(taskId)
                .memberId(memberId)
                .build());
        taskLogService.createTaskLog(taskId, "A member commented on the task", request.getComment());
        return CreateTaskCommentResponse.builder()
                .message("Task comment created successfully")
                .build();
    }
}
