package com.flowright.workspace_service.kafka.producer;

import java.util.UUID;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CheckMemberWorkspaceProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(UUID workspaceId, String email) {
        String message = workspaceId.toString() + "," + email;
        kafkaTemplate.send("check-member-workspace-request", message);
    }
}
