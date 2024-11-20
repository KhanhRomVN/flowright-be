package com.flowright.project_service.kafka.consumer;

import java.util.concurrent.CountDownLatch;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class GetAllTeamIdByMemberIdConsumer {
    private String response;
    private final CountDownLatch latch = new CountDownLatch(1);

    @KafkaListener(topics = "get-all-team-id-by-member-id-response", groupId = "project-service")
    public void listen(String message) {
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
