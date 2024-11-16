package com.flowright.member_service.kafka.consumer;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.flowright.member_service.dto.MemberDTO.MemberResponse;
import com.flowright.member_service.service.MemberService;

@Service
public class GetMemberInfoConsumer {
    @Autowired
    private MemberService memberService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "get-member-info-request", groupId = "member-service")
    public void consume(String message) {
        MemberResponse memberInfo = memberService.getMemberById(UUID.fromString(message));
        String messageResponse = memberInfo.getId() + "," + memberInfo.getUsername() + "," + memberInfo.getEmail();
        kafkaTemplate.send("get-member-info-response", messageResponse);
    }
}
