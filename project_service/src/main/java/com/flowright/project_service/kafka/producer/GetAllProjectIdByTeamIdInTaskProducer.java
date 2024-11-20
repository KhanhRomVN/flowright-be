package com.flowright.project_service.kafka.producer;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class GetAllProjectIdByTeamIdInTaskProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(UUID teamId) {
        kafkaTemplate.send("get-all-project-id-by-team-id-in-task-request", teamId.toString());
    }
}
