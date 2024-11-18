package com.flowright.task_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flowright.task_service.dto.TaskGroupDTO.CreateTaskGroupRequest;
import com.flowright.task_service.dto.TaskGroupDTO.CreateTaskGroupResponse;
import com.flowright.task_service.dto.TaskGroupDTO.GetAllTaskGroupsResponse;
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
        System.out.println(request);
        // check if task group name & project id already exists
        if (taskGroupRepository.existsByNameAndProjectId(request.getName(), UUID.fromString(request.getProjectId()))) {
            throw new RuntimeException("Task group name already exists");
        }
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

    public TaskGroup getTaskGroupById(UUID taskGroupId) {
        return taskGroupRepository
                .findById(taskGroupId)
                .orElseThrow(() -> new RuntimeException("Task group not found"));
    }

    public List<GetAllTaskGroupsResponse> getAllTaskGroups(UUID projectId) {
        List<TaskGroup> taskGroups = taskGroupRepository.findByProjectId(projectId);
        List<GetAllTaskGroupsResponse> response = new ArrayList<>();
        for (TaskGroup taskGroup : taskGroups) {
            response.add(GetAllTaskGroupsResponse.builder()
                    .id(taskGroup.getId())
                    .name(taskGroup.getName())
                    .description(taskGroup.getDescription())
                    .build());
        }
        return response;
    }
}
