package com.flowright.workspace_service.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateMemberWorkspaceProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String userId, String workspaceId, String email, String username, String roleId) {
        String message = userId + "," + workspaceId + "," + email + "," + username + "," + roleId;
        kafkaTemplate.send("create-member-workspace-request", message);
    }
}
