package com.flowright.project_service.kafka.producer;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class GetAllTeamIdByMemberIdProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(UUID memberId) {
        kafkaTemplate.send("get-all-team-id-by-member-id-request", memberId.toString());
    }
}
