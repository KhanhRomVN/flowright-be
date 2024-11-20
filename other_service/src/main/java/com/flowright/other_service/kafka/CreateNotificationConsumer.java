package com.flowright.other_service.kafka;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.flowright.other_service.service.NotificationService;

@Service
public class CreateNotificationConsumer {

    @Autowired
    private NotificationService notificationService;

    @KafkaListener(topics = "create-notification-request", groupId = "other-service")
    public void listen(String message) {
        String[] parts = message.split(",");
        UUID workspaceId = UUID.fromString(parts[0]);
        UUID memberId = UUID.fromString(parts[1]);
        String title = parts[2];
        String detail = parts[3];
        String uri = parts[4];

        notificationService.createNotification(workspaceId, memberId, title, detail, uri);
    }
}
