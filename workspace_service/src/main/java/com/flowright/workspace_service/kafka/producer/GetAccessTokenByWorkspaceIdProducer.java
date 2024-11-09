package com.flowright.workspace_service.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetAccessTokenByWorkspaceIdProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String userId, String memberId, String workspaceId, String roleId) {
        String message = userId + "," + memberId + "," + workspaceId + "," + roleId;
        kafkaTemplate.send("get-access-token-by-workspace-id-request", message);
    }
}
