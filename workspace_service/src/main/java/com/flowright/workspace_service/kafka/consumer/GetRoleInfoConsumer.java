package com.flowright.workspace_service.kafka.consumer;

import java.util.concurrent.CountDownLatch;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class GetRoleInfoConsumer {
    private String response;
    private final CountDownLatch latch = new CountDownLatch(1);
    
    @KafkaListener(topics = "get-role-info-response", groupId = "workspace-service")
    public void consume(String message) {
        this.response = message;
        latch.countDown();
    }

    public String getResponse() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return response;
    }
}

