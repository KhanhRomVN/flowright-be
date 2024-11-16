package com.flowright.task_service.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flowright.task_service.dto.TaskLinkDTO.CreateTaskLinkResponse;
import com.flowright.task_service.dto.TaskLinkDTO.DeleteTaskLinkResponse;
import com.flowright.task_service.entity.TaskLink;
import com.flowright.task_service.repository.TaskLinkRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskLinkService {
    @Autowired
    private final TaskLinkRepository taskLinkRepository;

    @Autowired
    private final TaskLogService taskLogService;

    public void createTaskLink(UUID taskId, String title, String link) {
        TaskLink taskLink =
                TaskLink.builder().taskId(taskId).title(title).link(link).build();

        taskLinkRepository.save(taskLink);
        taskLogService.createTaskLog(taskId, "Task link created", "Task link created successfully");
    }

    public CreateTaskLinkResponse createTaskLinkById(UUID taskId, String title, String link) {
        TaskLink taskLink =
                TaskLink.builder().taskId(taskId).title(title).link(link).build();

        taskLinkRepository.save(taskLink);

        return CreateTaskLinkResponse.builder()
                .message("Task link created successfully")
                .build();
    }

    public List<TaskLink> getAllTaskLinksByTaskId(UUID taskId) {
        return taskLinkRepository.findByTaskId(taskId);
    }

    public DeleteTaskLinkResponse deleteTaskLinkById(UUID taskLinkId) {
        taskLinkRepository.deleteById(taskLinkId);
        return DeleteTaskLinkResponse.builder()
                .message("Task link deleted successfully")
                .build();
    }
}
