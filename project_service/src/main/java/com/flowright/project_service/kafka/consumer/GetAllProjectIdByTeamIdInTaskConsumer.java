package com.flowright.project_service.kafka.consumer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class GetAllProjectIdByTeamIdInTaskConsumer {
    private String response;
    private CountDownLatch latch = new CountDownLatch(1);

    @KafkaListener(topics = "get-all-project-id-by-team-id-in-task-response", groupId = "project-service")
    public void listen(String message) {
        this.response = message;
        latch.countDown();
    }

    public String getResponse() {
        try {
            if (!latch.await(10, TimeUnit.SECONDS)) {
                throw new RuntimeException("Timeout waiting for Kafka response");
            }
            return response;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while waiting for response", e);
        } finally {
            // Reset for next use
            latch = new CountDownLatch(1);
        }
    }
}
