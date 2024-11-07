package com.flowright.member_service.service.kafka;

import java.util.UUID;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class CreateWorkspaceConsumer {

    @KafkaListener(topics = "create-workspace-topic", groupId = "member-service")
    public void listen(String workspaceIdString, String ownerIdString, String name, String description) {
        UUID workspaceId = UUID.fromString(workspaceIdString);
        UUID ownerId = UUID.fromString(ownerIdString);
        System.out.println("Received Message: " + workspaceId + " " + ownerId + " " + name + " " + description);
    }
}
