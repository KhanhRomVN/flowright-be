package com.flowright.workspace_service.kafka.producer;

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
        kafkaTemplate.send("get-user-info-request", ownerId.toString());
    }
}
