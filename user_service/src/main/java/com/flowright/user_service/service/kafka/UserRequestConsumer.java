package com.flowright.user_service.service.kafka;

import java.util.UUID;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.flowright.user_service.dto.UserDTO;
import com.flowright.user_service.service.UserService;

@Service
public class UserRequestConsumer {

    private final UserService userService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public UserRequestConsumer(UserService userService, KafkaTemplate<String, String> kafkaTemplate) {
        this.userService = userService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "user-request-topic", groupId = "user-service")
    public void listen(String ownerId) {
        UserDTO user = userService.getCurrentUser(UUID.fromString(ownerId));
        String responseMessage = user.getUsername() + "," + user.getEmail();
        kafkaTemplate.send("user-response-topic", responseMessage);
    }
}
