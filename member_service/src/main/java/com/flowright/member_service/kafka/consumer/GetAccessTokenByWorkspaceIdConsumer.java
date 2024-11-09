package com.flowright.member_service.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.flowright.member_service.service.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetAccessTokenByWorkspaceIdConsumer {
    private final JwtService jwtService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "get-access-token-by-workspace-id-request", groupId = "member-service")
    public void listen(String message) {
        String[] parts = message.split(",");
        String memberId = parts[0];
        String workspaceId = parts[1];
        String email = parts[2];
        String username = parts[3];

        String accessToken = jwtService.generateAccessToken(null, memberId, workspaceId, null);
        kafkaTemplate.send("get-access-token-by-workspace-id-response", accessToken);
    }
}
