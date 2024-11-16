package com.flowright.task_service.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flowright.task_service.entity.TaskLog;
import com.flowright.task_service.repository.TaskLogRepository;

@Service
public class TaskLogService {
    @Autowired
    private TaskLogRepository taskLogRepository;

    public List<TaskLog> getAllTaskLogsByTaskId(UUID taskId) {
        return taskLogRepository.findByTaskId(taskId);
    }
}
