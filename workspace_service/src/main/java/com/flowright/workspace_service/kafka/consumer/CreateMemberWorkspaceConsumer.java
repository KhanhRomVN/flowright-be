package com.flowright.workspace_service.kafka.consumer;

import java.util.concurrent.CountDownLatch;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateMemberWorkspaceConsumer {
    private String response;
    private final CountDownLatch latch = new CountDownLatch(1);

    @KafkaListener(topics = "create-member-workspace-response", groupId = "workspace-service")
    public void listen(String memberId) {
        this.response = memberId;
        latch.countDown();
    }

    public String getMemberId() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return response;
    }
}
