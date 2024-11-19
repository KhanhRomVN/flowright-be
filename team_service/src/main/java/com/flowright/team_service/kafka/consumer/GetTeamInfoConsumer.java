package com.flowright.team_service.kafka.consumer;

import java.util.UUID;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.flowright.team_service.entity.Team;
import com.flowright.team_service.service.TeamService;

@Service
public class GetTeamInfoConsumer {

    private final TeamService teamService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public GetTeamInfoConsumer(TeamService teamService, KafkaTemplate<String, String> kafkaTemplate) {
        this.teamService = teamService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "get-team-info-request", groupId = "team-service")
    public void listen(String teamId) {
        Team team = teamService.getTeamById(UUID.fromString(teamId));
        kafkaTemplate.send("get-team-info-response", team.getName());
    }
}
