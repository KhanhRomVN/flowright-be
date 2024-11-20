package com.flowright.project_service.kafka.producer;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CreateNotificationProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(UUID workspaceId, UUID memberId, String title, String detail, String uri, String type) {
        String message = workspaceId.toString() + "," + memberId.toString() + "," + title + "," + detail + "," + uri
                + "," + type;
        kafkaTemplate.send("create-notification-request", message);
    }
}
