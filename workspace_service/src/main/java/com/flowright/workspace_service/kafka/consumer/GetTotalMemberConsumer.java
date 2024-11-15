package com.flowright.workspace_service.kafka.consumer;

import java.util.concurrent.CountDownLatch;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetTotalMemberConsumer {
    private int totalMember;
    private final CountDownLatch latch = new CountDownLatch(1);

    @KafkaListener(topics = "get_total_member_response", groupId = "workspace-service")
    public void listen(String message) {
        this.totalMember = Integer.parseInt(message);
        latch.countDown();
    }

    public int getTotalMember() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return totalMember;
    }
}
