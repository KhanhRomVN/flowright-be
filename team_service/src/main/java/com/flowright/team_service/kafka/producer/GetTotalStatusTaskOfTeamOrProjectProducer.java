package com.flowright.team_service.kafka.producer;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class GetTotalStatusTaskOfTeamOrProjectProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(UUID id, String type) {
        String message = type + "," + id.toString();
        kafkaTemplate.send("get-total-status-task-of-team-or-project-request", message);
    }
}
