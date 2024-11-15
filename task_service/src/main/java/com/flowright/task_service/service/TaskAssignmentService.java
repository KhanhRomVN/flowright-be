package com.flowright.task_service.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public List<UUID> getAllTaskTeam(UUID teamId) {
        return taskAssignmentRepository.findAllByTeamId(teamId).stream()
                .map(TaskAssignment::getTaskId)
                .collect(Collectors.toList());
    }
}
