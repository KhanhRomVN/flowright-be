package com.flowright.user_service.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {

    @KafkaListener(topics = "user-topic", groupId = "user-service")
    public void listen(String message) {
        System.out.println("Received Message: " + message);
    }
}
