package com.flowright.task_service.controller;

import java.util.List;
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

import com.flowright.task_service.dto.TaskGroupDTO.CreateTaskGroupRequest;
import com.flowright.task_service.dto.TaskGroupDTO.CreateTaskGroupResponse;
import com.flowright.task_service.dto.TaskGroupDTO.GetAllTaskGroupsResponse;
import com.flowright.task_service.service.JwtService;
import com.flowright.task_service.service.TaskGroupService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/task/service/task-groups")
@RequiredArgsConstructor
public class TaskGroupController {
    private final JwtService jwtService;
    private final TaskGroupService taskGroupService;

    // create task group: /task/service/task-groups
    @PostMapping
    public ResponseEntity<CreateTaskGroupResponse> createTaskGroup(
            @Valid @RequestBody CreateTaskGroupRequest request, @RequestHeader("access_token") String token) {
        jwtService.validateToken(token);
        return ResponseEntity.ok(taskGroupService.createTaskGroup(request));
    }

    // get all task groups: /task/service/task-groups?projectId=<projectId>
    @GetMapping
    public ResponseEntity<List<GetAllTaskGroupsResponse>> getAllTaskGroups(
            @RequestParam String projectId, @RequestHeader("access_token") String token) {
        jwtService.validateToken(token);
        return ResponseEntity.ok(taskGroupService.getAllTaskGroups(UUID.fromString(projectId)));
    }
}
