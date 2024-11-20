package com.flowright.task_service.kafka.consumer;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.flowright.task_service.service.TaskService;

@Service
public class GetTotalStatusTaskOfTeamOrProjectConsumer {
    @Autowired
    private TaskService taskService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "get-total-status-task-of-team-or-project-request", groupId = "task-service")
    public void consume(String message) {
        String[] parts = message.split(",");
        String type = parts[0];
        UUID id = UUID.fromString(parts[1]);
        if (type.equals("team")) {
            String response = taskService.getTotalStatusTaskOfTeam(id);
            kafkaTemplate.send("get-total-status-task-of-team-or-project-response", response);
        } else {
            String response = taskService.getTotalStatusTaskOfProject(id);
            kafkaTemplate.send("get-total-status-task-of-team-or-project-response", response);
        }
    }
}
