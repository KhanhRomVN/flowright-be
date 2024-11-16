package com.flowright.task_service.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flowright.task_service.dto.TaskDTO.CreateTaskRequest;
import com.flowright.task_service.dto.TaskDTO.CreateTaskResponse;
import com.flowright.task_service.dto.TaskDTO.GetAllTaskTeamListResponse;
import com.flowright.task_service.dto.TaskDTO.GetAllTaskWorkspaceResponse;
import com.flowright.task_service.dto.TaskDTO.GetTaskResponse;
import com.flowright.task_service.dto.TaskDTO.UpdateTaskDTO.updateNameTaskRequest;
import com.flowright.task_service.dto.TaskDTO.UpdateTaskResponse;
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

    // get task: /task/service/tasks
    @GetMapping
    public ResponseEntity<GetTaskResponse> getTaskByTaskId(@RequestParam String taskId) {
        return ResponseEntity.ok(taskService.getTaskByTaskId(UUID.fromString(taskId)));
    }

    // get all task team: /task/service/tasks/team?teamId=
    @GetMapping("/team")
    public ResponseEntity<GetAllTaskTeamListResponse> getAllTaskTeam(@RequestParam String teamId) {
        return ResponseEntity.ok(taskService.getAllTaskTeam(UUID.fromString(teamId)));
    }

    // get all task workspace: /task/service/tasks/workspace
    @GetMapping("/workspace")
    public ResponseEntity<GetAllTaskWorkspaceResponse> getAllTaskWorkspace(
            @RequestHeader("access_token") String token) {
        return ResponseEntity.ok(taskService.getAllTaskWorkspace(token));
    }

    // update task: /task/service/tasks/name?taskId=?name=
    @PutMapping("/name")
    public ResponseEntity<UpdateTaskResponse> updateTaskName(
            @Valid @RequestBody updateNameTaskRequest request, @RequestHeader("access_token") String token) {
        jwtService.validateToken(token);
        return ResponseEntity.ok(taskService.updateTaskName(request.getName(), UUID.fromString(request.getTaskId())));
    }

    // update task: /task/service/tasks/description?taskId=?description=
    @PutMapping("/description")
    public ResponseEntity<UpdateTaskResponse> updateTaskDescription(
            @Valid @RequestBody String description,
            @RequestParam String taskId,
            @RequestHeader("access_token") String token) {
        jwtService.validateToken(token);
        return ResponseEntity.ok(taskService.updateTaskDescription(description, UUID.fromString(taskId)));
    }

    // update task: /task/service/tasks/priority?taskId=?priority=
    @PutMapping("/priority")
    public ResponseEntity<UpdateTaskResponse> updateTaskPriority(
            @Valid @RequestBody String priority,
            @RequestParam String taskId,
            @RequestHeader("access_token") String token) {
        jwtService.validateToken(token);
        return ResponseEntity.ok(taskService.updateTaskPriority(priority, UUID.fromString(taskId)));
    }

    // update task: /task/service/tasks/status?taskId=?status=
    @PutMapping("/status")
    public ResponseEntity<UpdateTaskResponse> updateTaskStatus(
            @Valid @RequestBody String status,
            @RequestParam String taskId,
            @RequestHeader("access_token") String token) {
        jwtService.validateToken(token);
        return ResponseEntity.ok(taskService.updateTaskStatus(status, UUID.fromString(taskId)));
    }

    // update task: /task/service/tasks/startDate?taskId=?startDate=
    @PutMapping("/startDate")
    public ResponseEntity<UpdateTaskResponse> updateTaskStartDate(
            @Valid @RequestBody LocalDateTime startDate,
            @RequestParam String taskId,
            @RequestHeader("access_token") String token) {
        jwtService.validateToken(token);
        return ResponseEntity.ok(taskService.updateTaskStartDate(startDate, UUID.fromString(taskId)));
    }

    // update task: /task/service/tasks/endDate?taskId=
    @PutMapping("/endDate")
    public ResponseEntity<UpdateTaskResponse> updateTaskEndDate(
            @Valid @RequestBody LocalDateTime endDate,
            @RequestParam String taskId,
            @RequestHeader("access_token") String token) {
        jwtService.validateToken(token);
        return ResponseEntity.ok(taskService.updateTaskEndDate(endDate, UUID.fromString(taskId)));
    }
}
