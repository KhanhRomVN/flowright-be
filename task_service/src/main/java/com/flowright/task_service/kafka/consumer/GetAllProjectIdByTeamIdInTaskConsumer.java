package com.flowright.task_service.kafka.consumer;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.flowright.task_service.service.TaskService;

@Service
public class GetAllProjectIdByTeamIdInTaskConsumer {
    @Autowired
    private TaskService taskService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "get-all-project-id-by-team-id-in-task-request", groupId = "task-service")
    public void consume(String teamId) {
        String projectIds = taskService.getAllProjectIdByTeamIdInTask(UUID.fromString(teamId));
        kafkaTemplate.send("get-all-project-id-by-team-id-in-task-response", projectIds);
    }
}
