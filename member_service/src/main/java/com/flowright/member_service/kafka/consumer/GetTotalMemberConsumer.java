package com.flowright.member_service.kafka.consumer;

import java.util.UUID;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.flowright.member_service.service.MemberService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetTotalMemberConsumer {
    private final MemberService memberService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "get_total_member_request", groupId = "member-service")
    public void listen(String message) {
        UUID workspaceId = UUID.fromString(message);
        Integer totalMember = memberService.getTotalMember(workspaceId);
        kafkaTemplate.send("get_total_member_response", totalMember.toString());
    }
}
