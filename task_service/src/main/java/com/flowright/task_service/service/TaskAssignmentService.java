package com.flowright.task_service.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.flowright.task_service.entity.TaskAssignment;
import com.flowright.task_service.repository.TaskAssignmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskAssignmentService {
    private final TaskAssignmentRepository taskAssignmentRepository;

    public void createTaskAssignment(UUID taskId, UUID memberId, UUID teamId) {
        TaskAssignment taskAssignment = TaskAssignment.builder()
                .taskId(taskId)
                .memberId(memberId)
                .teamId(teamId)
                .build();

        taskAssignmentRepository.save(taskAssignment);
    }
}
