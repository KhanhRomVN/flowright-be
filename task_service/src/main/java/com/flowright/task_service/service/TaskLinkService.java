package com.flowright.task_service.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.flowright.task_service.dto.TaskLinkDTO.CreateTaskLinkResponse;
import com.flowright.task_service.entity.TaskLink;
import com.flowright.task_service.repository.TaskLinkRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskLinkService {
    private final TaskLinkRepository taskLinkRepository;

    public void createTaskLink(UUID taskId, String title, String link) {
        TaskLink taskLink =
                TaskLink.builder().taskId(taskId).title(title).link(link).build();

        taskLinkRepository.save(taskLink);
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
}
