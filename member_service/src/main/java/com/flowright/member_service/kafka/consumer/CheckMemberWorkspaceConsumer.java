package com.flowright.member_service.kafka.consumer;

import java.util.UUID;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.flowright.member_service.service.MemberService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CheckMemberWorkspaceConsumer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final MemberService memberService;

    @KafkaListener(topics = "check-member-workspace-request", groupId = "member-service")
    public void listen(String message) {
        String[] parts = message.split(",");
        UUID workspaceId = UUID.fromString(parts[0]);
        String email = parts[1];

        boolean exists = memberService.checkMemberWorkspace(workspaceId, email);
        kafkaTemplate.send("check-member-workspace-response", exists ? "true" : "false");
    }
}
