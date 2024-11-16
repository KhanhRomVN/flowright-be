package com.flowright.task_service.controller;

import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flowright.task_service.dto.TaskCommentDTO.CreateTaskCommentRequest;
import com.flowright.task_service.dto.TaskCommentDTO.CreateTaskCommentResponse;
import com.flowright.task_service.service.JwtService;
import com.flowright.task_service.service.TaskCommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/task/service/task-comments")
@RequiredArgsConstructor
public class TaskCommentController {
    @Autowired
    private final TaskCommentService taskCommentService;

    @Autowired
    private final JwtService jwtService;

    // Create a task comment: /task/service/task-comments?taskId=<taskId>
    @PostMapping
    public CreateTaskCommentResponse createTaskComment(
            @Valid @RequestBody CreateTaskCommentRequest request,
            @RequestParam String taskId,
            @RequestHeader("access_token") String token) {
        UUID memberId = jwtService.extractMemberId(token);
        return taskCommentService.createTaskComment(request, UUID.fromString(taskId), memberId);
    }
}
