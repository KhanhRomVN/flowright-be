package com.flowright.member_service.kafka.consumer;

import java.util.UUID;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.flowright.member_service.service.MemberService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateMemberWorkspaceConsumer {
    private final MemberService memberService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "create-member-workspace-request", groupId = "member-service")
    public void listen(String message) {
        String[] parts = message.split(",");
        UUID userId = UUID.fromString(parts[0]);
        UUID workspaceId = UUID.fromString(parts[1]);
        String email = parts[2];
        String username = parts[3];
        UUID roleId = UUID.fromString(parts[4]);
        String memberId = memberService.createMember(userId, workspaceId, email, username, roleId);
        kafkaTemplate.send("create-member-workspace-response", memberId);
    }
}
