package com.flowright.project_service.kafka.consumer;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.flowright.project_service.service.ProjectService;

@Service
public class GetRoleInfoConsumer {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "get-project-info-request", groupId = "project-service")
    public void consume(String message) {
        String projectName = projectService.getProjectNameById(UUID.fromString(message));
        kafkaTemplate.send("get-project-info-response", projectName);
    }
}
