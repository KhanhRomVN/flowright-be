package com.flowright.workspace_service.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetAccessTokenByWorkspaceIdConsumer {
    private String accessToken;

    @KafkaListener(topics = "get-access-token-by-workspace-id-response", groupId = "workspace-service")
    public void listen(String message) {
        String[] parts = message.split(",");
        accessToken = parts[0];
    }

    public String getAccessToken() {
        return accessToken;
    }
}
