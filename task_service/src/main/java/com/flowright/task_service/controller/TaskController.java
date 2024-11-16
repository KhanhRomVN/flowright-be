package com.flowright.task_service.controller;

import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flowright.task_service.dto.TaskDTO.CreateTaskRequest;
import com.flowright.task_service.dto.TaskDTO.CreateTaskResponse;
import com.flowright.task_service.dto.TaskDTO.GetAllTaskTeamListResponse;
import com.flowright.task_service.dto.TaskDTO.GetAllTaskWorkspaceResponse;
import com.flowright.task_service.service.JwtService;
import com.flowright.task_service.service.TaskService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/task/service/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final JwtService jwtService;
    private final TaskService taskService;

    // create task: /task/service/tasks
    @PostMapping
    public ResponseEntity<CreateTaskResponse> createTask(
            @Valid @RequestBody CreateTaskRequest request, @RequestHeader("access_token") String token) {
        UUID creatorId = jwtService.extractUserId(token);
        return ResponseEntity.ok(taskService.createTask(request, creatorId));
    }

    // get all task team: /task/service/tasks?teamId=
    @GetMapping
    public ResponseEntity<GetAllTaskTeamListResponse> getAllTaskTeam(@RequestParam String teamId) {
        return ResponseEntity.ok(taskService.getAllTaskTeam(UUID.fromString(teamId)));
    }

    // get all task workspace: /task/service/tasks/workspace
    @GetMapping("/workspace")
    public ResponseEntity<GetAllTaskWorkspaceResponse> getAllTaskWorkspace(
            @RequestHeader("access_token") String token) {
        return ResponseEntity.ok(taskService.getAllTaskWorkspace(token));
    }
}
