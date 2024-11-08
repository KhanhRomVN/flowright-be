package com.flowright.workspace_service.service.kafka;

import java.util.UUID;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CreateWorkspaceProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public CreateWorkspaceProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(UUID workspaceId, UUID ownerId, String name, String description) {
        String message = workspaceId + "," + ownerId + "," + name + "," + description;
        kafkaTemplate.send("create-workspace-topic", message);
    }
}
