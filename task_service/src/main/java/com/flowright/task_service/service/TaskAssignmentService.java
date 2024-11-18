package com.flowright.task_service.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flowright.task_service.entity.TaskAssignment;
import com.flowright.task_service.repository.TaskAssignmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskAssignmentService {
    @Autowired
    private final TaskAssignmentRepository taskAssignmentRepository;

    @Autowired
    private final TaskLogService taskLogService;

    public void createTaskAssignment(UUID taskId, UUID memberId, UUID teamId) {
        TaskAssignment taskAssignment = TaskAssignment.builder()
                .taskId(taskId)
                .memberId(memberId)
                .teamId(teamId)
                .build();

        taskAssignmentRepository.save(taskAssignment);
        taskLogService.createTaskLog(taskId, "Task assignment created", "Task assignment created successfully");
    }

    public List<UUID> getAllTaskAssignmentTeamId(UUID teamId) {
        return taskAssignmentRepository.findAllByTeamId(teamId).stream()
                .map(TaskAssignment::getTaskId)
                .collect(Collectors.toList());
    }

    public List<TaskAssignment> getAllTaskAssignmentByTaskId(UUID taskId) {
        return taskAssignmentRepository.findAllByTaskId(taskId);
    }

    public List<UUID> getAllTaskAssignmentMemberId(UUID memberId) {
        return taskAssignmentRepository.findAllByMemberId(memberId).stream()
                .map(TaskAssignment::getTaskId)
                .collect(Collectors.toList());
    }

    public void deleteTaskAssignment(UUID taskAssignmentId) {
        taskAssignmentRepository.deleteById(taskAssignmentId);
    }
}
