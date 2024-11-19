package com.flowright.team_service.kafka.producer;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class GetAllTodoProgressTaskMemberTeamIdProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(UUID teamId, UUID memberId) {
        String message = teamId.toString() + "," + memberId.toString();
        kafkaTemplate.send("get-all-todo-progress-task-member-team-id-request", message);
    }
}
