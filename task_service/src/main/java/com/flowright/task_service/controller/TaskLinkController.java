package com.flowright.task_service.controller;

import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flowright.task_service.dto.TaskLinkDTO.CreateTaskLinkRequest;
import com.flowright.task_service.dto.TaskLinkDTO.CreateTaskLinkResponse;
import com.flowright.task_service.dto.TaskLinkDTO.DeleteTaskLinkResponse;
import com.flowright.task_service.service.JwtService;
import com.flowright.task_service.service.TaskLinkService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/task/service/task-links")
@RequiredArgsConstructor
public class TaskLinkController {
    private final TaskLinkService taskLinkService;
    private final JwtService jwtService;

    // Create a task link by task id: /task/service/task-links?taskId=
    @PostMapping
    public ResponseEntity<CreateTaskLinkResponse> createTaskLink(
            @Valid @RequestBody CreateTaskLinkRequest request,
            @RequestParam String taskId,
            @RequestHeader("access_token") String token) {
        jwtService.validateToken(token);
        return ResponseEntity.ok(
                taskLinkService.createTaskLinkById(UUID.fromString(taskId), request.getTitle(), request.getLink()));
    }

    // delete a task link by task link id: /task/service/task-links?taskLinkId=
    @DeleteMapping
    public ResponseEntity<DeleteTaskLinkResponse> deleteTaskLink(
            @RequestParam UUID taskLinkId, @RequestHeader("access_token") String token) {
        jwtService.validateToken(token);
        return ResponseEntity.ok(taskLinkService.deleteTaskLinkById(taskLinkId));
    }
}
