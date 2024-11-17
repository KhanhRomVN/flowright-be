package com.flowright.user_service.kafka.consumer;

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

    @KafkaListener(topics = "get-user-info-request", groupId = "user-service")
    public void listen(String ownerId) {
        UserDTO user = userService.getCurrentUser(UUID.fromString(ownerId));
        System.out.println("user = " + user);
        String responseMessage = user.getUsername() + "," + user.getEmail();
        kafkaTemplate.send("get-user-info-response", responseMessage);
    }
}
