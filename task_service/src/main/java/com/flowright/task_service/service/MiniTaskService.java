package com.flowright.task_service.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flowright.task_service.dto.MiniTaskDTO.ChangeMiniTaskStatusResponse;
import com.flowright.task_service.dto.MiniTaskDTO.CreateMiniTaskResponse;
import com.flowright.task_service.dto.MiniTaskDTO.DeleteMiniTaskResponse;
import com.flowright.task_service.entity.MiniTask;
import com.flowright.task_service.repository.MiniTaskRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MiniTaskService {
    @Autowired
    private final MiniTaskRepository miniTaskRepository;

    @Autowired
    private final TaskLogService taskLogService;

    public void createMiniTask(UUID taskId, String name, String description, String status, UUID memberId) {
        MiniTask miniTask = MiniTask.builder()
                .taskId(taskId)
                .name(name)
                .description(description)
                .status(status)
                .memberId(memberId)
                .build();

        miniTaskRepository.save(miniTask);
        taskLogService.createTaskLog(taskId, "Mini task created", "Mini task created successfully");
    }

    public CreateMiniTaskResponse createMiniTaskById(UUID taskId, String name, String description, UUID memberId) {
        createMiniTask(taskId, name, description, "in_progress", memberId);
        taskLogService.createTaskLog(taskId, "Mini task created", "Mini task created successfully");
        return CreateMiniTaskResponse.builder()
                .message("Mini task created successfully")
                .build();
    }

    public List<MiniTask> getAllMiniTasksByTaskId(UUID taskId) {
        return miniTaskRepository.findByTaskId(taskId);
    }

    public DeleteMiniTaskResponse deleteMiniTaskById(UUID miniTaskId) {
        miniTaskRepository.deleteById(miniTaskId);
        taskLogService.createTaskLog(miniTaskId, "Mini task deleted", "Mini task deleted successfully");
        return DeleteMiniTaskResponse.builder()
                .message("Mini task deleted successfully")
                .build();
    }

    public ChangeMiniTaskStatusResponse changeMiniTaskStatusById(UUID miniTaskId, String status) {
        MiniTask miniTask =
                miniTaskRepository.findById(miniTaskId).orElseThrow(() -> new RuntimeException("Mini task not found"));
        miniTask.setStatus(status);
        miniTaskRepository.save(miniTask);
        taskLogService.createTaskLog(
                miniTask.getTaskId(), "Mini task status changed", "Mini task status changed successfully");
        return ChangeMiniTaskStatusResponse.builder()
                .message("Mini task status changed successfully")
                .build();
    }

    public String updateMiniTaskMemberId(UUID miniTaskId, UUID memberId) {
        MiniTask miniTask =
                miniTaskRepository.findById(miniTaskId).orElseThrow(() -> new RuntimeException("Mini task not found"));
        miniTask.setMemberId(memberId);
        miniTaskRepository.save(miniTask);
        return "Mini task member id updated successfully";
    }
}
