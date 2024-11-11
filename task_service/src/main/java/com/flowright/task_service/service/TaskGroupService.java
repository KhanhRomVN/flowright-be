package com.flowright.task_service.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flowright.task_service.dto.TaskGroupDTO.CreateTaskGroupRequest;
import com.flowright.task_service.dto.TaskGroupDTO.CreateTaskGroupResponse;
import com.flowright.task_service.entity.TaskGroup;
import com.flowright.task_service.repository.TaskGroupRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskGroupService {
    @Autowired
    private final TaskGroupRepository taskGroupRepository;

    @Transactional
    public CreateTaskGroupResponse createTaskGroup(CreateTaskGroupRequest request) {
        TaskGroup taskGroup = TaskGroup.builder()
                .name(request.getName())
                .description(request.getDescription())
                .projectId(UUID.fromString(request.getProjectId()))
                .build();
        taskGroupRepository.save(taskGroup);
        return CreateTaskGroupResponse.builder()
                .message("Task group created successfully")
                .build();
    }
}
