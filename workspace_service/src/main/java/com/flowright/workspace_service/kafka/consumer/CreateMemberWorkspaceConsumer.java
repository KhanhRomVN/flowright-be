package com.flowright.workspace_service.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateMemberWorkspaceConsumer {
    private String memberId;
    
    @KafkaListener(topics = "create-member-workspace-response", groupId = "workspace-service")
    public void listen(String message) {
        this.memberId = message;
    }

    public String getMemberId() {
        return memberId;
    }
}

