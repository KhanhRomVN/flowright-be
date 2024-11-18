package com.flowright.team_service.kafka.consumer;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class GetMemberInfoTimerConsumer {
    private final ConcurrentHashMap<UUID, CompletableFuture<String>> responses = new ConcurrentHashMap<>();

    public CompletableFuture<String> waitForResponse(UUID memberId) {
        CompletableFuture<String> future = new CompletableFuture<>();
        responses.put(memberId, future);
        return future;
    }

    @KafkaListener(topics = "get-member-timer-info-response", groupId = "team-service")
    public void consume(String message) {
        // Find the waiting future and complete it
        responses.forEach((memberId, future) -> {
            future.complete(message);
            responses.remove(memberId);
        });
    }
}
