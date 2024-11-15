package com.flowright.workspace_service.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CheckMemberWorkspaceConsumer {
    private String response;

    @KafkaListener(topics = "check-member-workspace-response", groupId = "workspace-service")
    public void listen(String message) {
        response = message;
    }

    public String getResponse() {
        return response;
    }
}
