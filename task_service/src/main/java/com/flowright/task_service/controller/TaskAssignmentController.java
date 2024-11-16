package com.flowright.task_service.controller;

import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flowright.task_service.dto.TaskAssignmentDTO.AddTaskAssignmentRequest;
import com.flowright.task_service.service.JwtService;
import com.flowright.task_service.service.TaskAssignmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/task/service/task-assignments")
@RequiredArgsConstructor
public class TaskAssignmentController {
    @Autowired
    private final TaskAssignmentService taskAssignmentService;

    @Autowired
    private final JwtService jwtService;

    // Create a task comment: /task/service/task-comments?taskId=<taskId>
    @PostMapping
    public String createTaskAssignment(
            @Valid @RequestBody AddTaskAssignmentRequest request,
            @RequestParam String taskId,
            @RequestHeader("access_token") String token) {
        jwtService.validateToken(token);
        taskAssignmentService.createTaskAssignment(
                UUID.fromString(taskId), UUID.fromString(request.getMemberId()), UUID.fromString(request.getTeamId()));
        return "Task assignment created successfully";
    }

    // delete a task assignment: /task/service/task-assignments?taskAssignmentId=<taskAssignmentId>
    @DeleteMapping
    public String deleteTaskAssignment(
            @RequestParam String taskAssignmentId, @RequestHeader("access_token") String token) {
        jwtService.validateToken(token);
        taskAssignmentService.deleteTaskAssignment(UUID.fromString(taskAssignmentId));
        return "Task assignment deleted successfully";
    }
}
