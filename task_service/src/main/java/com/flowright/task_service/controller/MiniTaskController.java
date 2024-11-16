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

import com.flowright.task_service.dto.MiniTaskDTO.CreateMiniTaskRequest;
import com.flowright.task_service.dto.MiniTaskDTO.CreateMiniTaskResponse;
import com.flowright.task_service.dto.MiniTaskDTO.DeleteMiniTaskResponse;
import com.flowright.task_service.service.JwtService;
import com.flowright.task_service.service.MiniTaskService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/task/service/mini-tasks")
@RequiredArgsConstructor
public class MiniTaskController {
    private final MiniTaskService miniTaskService;
    private final JwtService jwtService;

    // Create a mini task by task id: /task/service/mini-tasks?taskId=
    @PostMapping
    public ResponseEntity<CreateMiniTaskResponse> createMiniTask(
            @Valid @RequestBody CreateMiniTaskRequest request,
            @RequestParam String taskId,
            @RequestHeader("access_token") String token) {
        UUID memberId = jwtService.extractMemberId(token);
        return ResponseEntity.ok(miniTaskService.createMiniTaskById(
                UUID.fromString(taskId), request.getName(), request.getDescription(), memberId));
    }

    // Delete a mini task by mini task id: /task/service/mini-tasks?miniTaskId=
    @DeleteMapping
    public ResponseEntity<DeleteMiniTaskResponse> deleteMiniTask(@RequestParam UUID miniTaskId) {
        return ResponseEntity.ok(miniTaskService.deleteMiniTaskById(miniTaskId));
    }
}
