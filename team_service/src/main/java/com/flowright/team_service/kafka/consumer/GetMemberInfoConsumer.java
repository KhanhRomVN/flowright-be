package com.flowright.team_service.kafka.consumer;

import java.util.concurrent.CountDownLatch;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class GetMemberInfoConsumer {
    private String response;
    private final CountDownLatch latch = new CountDownLatch(1);

    @KafkaListener(topics = "get-member-info-response", groupId = "team-service")
    public void listen(String message) {
        System.out.println(message);
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
