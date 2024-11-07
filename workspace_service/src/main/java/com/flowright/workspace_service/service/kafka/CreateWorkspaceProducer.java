package com.flowright.workspace_service.service.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CreateWorkspaceProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public CreateWorkspaceProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String workspaceIdString, String ownerIdString, String name, String description) {
        String message = workspaceIdString + "," + ownerIdString + "," + name + "," + description;
        kafkaTemplate.send("create-workspace-topic", message);
    }
}
