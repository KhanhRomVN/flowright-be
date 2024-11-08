package com.flowright.workspace_service.service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class GetUserInfoConsumer {
    private String response;

    @KafkaListener(topics = "user-response-topic", groupId = "workspace-service")
    public void listen(String message) {
        this.response = message;
    }

    public String getResponse() {
        return response;    
    }
}
