package com.flowright.task_service.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

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
}
