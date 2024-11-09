package com.flowright.workspace_service.kafka.producer;

import java.util.UUID;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetTotalMemberProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(UUID workspaceId) {
        kafkaTemplate.send("get_total_member_request", workspaceId.toString());
    }
}
