package com.flowright.task_service.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flowright.task_service.entity.MiniTask;
import com.flowright.task_service.repository.MiniTaskRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MiniTaskService {
    @Autowired
    private final MiniTaskRepository miniTaskRepository;

    public void createMiniTask(
            UUID taskId, String name, String description, String status, UUID teamId, UUID memberId) {
        MiniTask miniTask = MiniTask.builder()
                .taskId(taskId)
                .name(name)
                .description(description)
                .status(status)
                .teamId(teamId)
                .memberId(memberId)
                .build();

        miniTaskRepository.save(miniTask);
    }
}
