package com.flowright.workspace_service.kafka.consumer;

import java.util.concurrent.CountDownLatch;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetAccessTokenByWorkspaceIdConsumer {
    private String accessToken;
    private final CountDownLatch latch = new CountDownLatch(1);
    @KafkaListener(topics = "get-access-token-by-workspace-id-response", groupId = "workspace-service")
    public void listen(String message) {
        String[] parts = message.split(",");
        accessToken = parts[0];
        latch.countDown();
    }

    public String getAccessToken() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return accessToken;
    }
}
