package com.flowright.task_service.kafka.producer;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class GetMemberInfoProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(UUID memberId) {
        kafkaTemplate.send("get-member-info-request", memberId.toString());
    }
}
