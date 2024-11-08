package com.flowright.workspace_service.service.kafka;

import java.util.UUID;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class GetUserInfoProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public GetUserInfoProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(UUID ownerId) {
        kafkaTemplate.send("user-request-topic", ownerId.toString());
    }
}
