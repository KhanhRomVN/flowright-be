package com.flowright.team_service.kafka.consumer;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.flowright.team_service.service.TeamMemberService;

@Service
public class GetAllTeamIdByMemberIdConsumer {
    @Autowired
    private TeamMemberService teamMemberService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "get-all-team-id-by-member-id-request", groupId = "team-service")
    public void consume(String message) {
        List<UUID> teamIds = teamMemberService.getAllTeamIdByMemberId(UUID.fromString(message));
        String messageResponse =
                String.join(",", teamIds.stream().map(UUID::toString).collect(Collectors.toList()));
        kafkaTemplate.send("get-all-team-id-by-member-id-response", messageResponse);
    }
}
