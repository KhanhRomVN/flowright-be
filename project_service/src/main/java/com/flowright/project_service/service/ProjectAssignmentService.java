package com.flowright.project_service.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flowright.project_service.entity.ProjectAssignment;
import com.flowright.project_service.repository.ProjectAssignmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectAssignmentService {
    @Autowired
    private final ProjectAssignmentRepository projectAssignmentRepository;

    public void createProjectAssignment(UUID projectId, UUID teamId) {
        ProjectAssignment projectAssignment =
                ProjectAssignment.builder().projectId(projectId).teamId(teamId).build();

        projectAssignmentRepository.save(projectAssignment);
    }
}
