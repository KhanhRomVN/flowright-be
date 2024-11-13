package com.flowright.task_service.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flowright.task_service.dto.MiniTaskDTO.CreateMiniTaskRequest;
import com.flowright.task_service.dto.TaskAssignmentDTO.CreateTaskAssignmentRequest;
import com.flowright.task_service.dto.TaskDTO.CreateTaskRequest;
import com.flowright.task_service.dto.TaskDTO.CreateTaskResponse;
import com.flowright.task_service.dto.TaskLinkDTO.CreateTaskLinkRequest;
import com.flowright.task_service.entity.Task;
import com.flowright.task_service.repository.TaskRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {
    @Autowired
    private final TaskRepository taskRepository;

    private final TaskAssignmentService taskAssignmentService;
    private final TaskLinkService taskLinkService;
    private final MiniTaskService miniTaskService;

    public CreateTaskResponse createTask(CreateTaskRequest request, UUID creatorId) {
        Task task = Task.builder()
                .name(request.getName())
                .description(request.getDescription())
                .creatorId(creatorId)
                .ownerId(UUID.fromString(request.getOwnerId()))
                .projectId(UUID.fromString(request.getProjectId()))
                .priority(request.getPriority())
                .startDate(LocalDateTime.parse(request.getStartDate()))
                .endDate(request.getEndDate() != null ? LocalDateTime.parse(request.getEndDate()) : null)
                .build();

        Task savedTask = taskRepository.save(task);

        if (request.getTaskAssignments() != null) {
            for (CreateTaskAssignmentRequest taskAssignment : request.getTaskAssignments()) {
                taskAssignmentService.createTaskAssignment(
                        savedTask.getId(),
                        UUID.fromString(taskAssignment.getMemberId()),
                        UUID.fromString(taskAssignment.getTeamId()));
            }
        }

        if (request.getTaskLinks() != null) {
            for (CreateTaskLinkRequest taskLink : request.getTaskLinks()) {
                taskLinkService.createTaskLink(savedTask.getId(), taskLink.getTitle(), taskLink.getLink());
            }
        }

        if (request.getMiniTasks() != null) {
            for (CreateMiniTaskRequest miniTask : request.getMiniTasks()) {
                miniTaskService.createMiniTask(
                        savedTask.getId(),
                        miniTask.getName(),
                        miniTask.getDescription(),
                        "todo",
                        UUID.fromString(miniTask.getTeamId()),
                        UUID.fromString(miniTask.getMemberId()));
            }
        }

        return CreateTaskResponse.builder().message("Task created successfully").build();
    }
}
