package com.flowright.member_service.kafka.consumer;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.flowright.member_service.service.RoleService;

@Service
public class GetRoleInfoConsumer {
    @Autowired
    private RoleService roleService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "get-role-info-request", groupId = "member-service")
    public void consume(String message) {
        String roleName = roleService.getRoleNameById(UUID.fromString(message));
        kafkaTemplate.send("get-role-info-response", roleName);
    }
}
