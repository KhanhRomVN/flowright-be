package com.flowright.project_service.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flowright.project_service.dto.ProjectDTO.CreateProjectRequest;
import com.flowright.project_service.dto.ProjectDTO.CreateProjectResponse;
import com.flowright.project_service.dto.ProjectDTO.GetAllProjectsResponse;
import com.flowright.project_service.service.JwtService;
import com.flowright.project_service.service.ProjectService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project-service/projects")
public class ProjectController {
    private final ProjectService projectService;
    private final JwtService jwtService;

    // create project: /project-service/projects
    @PostMapping
    public ResponseEntity<CreateProjectResponse> createProject(
            @RequestBody CreateProjectRequest request, @RequestHeader("access_token") String token) {
        UUID creatorId = jwtService.extractUserId(token);
        UUID workspaceId = jwtService.extractWorkspaceId(token);
        return ResponseEntity.ok(projectService.createProject(request, workspaceId, creatorId));
    }

    // get all projects: /project-service/projects
    @GetMapping
    public ResponseEntity<GetAllProjectsResponse> getAllProjects(@RequestHeader("access_token") String token) {
        UUID workspaceId = jwtService.extractWorkspaceId(token);
        return ResponseEntity.ok(projectService.getAllProjects(workspaceId));
    }
}
