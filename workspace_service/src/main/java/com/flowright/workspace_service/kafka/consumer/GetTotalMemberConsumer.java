package com.flowright.workspace_service.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetTotalMemberConsumer {
    private int totalMember;

    @KafkaListener(topics = "get_total_member_response", groupId = "workspace_service")
    public void listen(String message) {
        this.totalMember = Integer.parseInt(message);
    }

    public int getTotalMember() {
        return totalMember;
    }
}

