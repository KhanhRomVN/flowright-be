package com.flowright.member_service.service.kafka;

import java.util.UUID;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.flowright.member_service.service.MemberService;
import com.flowright.member_service.service.RoleService;

@Service
public class CreateWorkspaceConsumer {

    private final RoleService roleService;
    private final MemberService memberService;

    public CreateWorkspaceConsumer(
            RoleService roleService, MemberService memberService, KafkaTemplate<String, String> kafkaTemplate) {
        this.roleService = roleService;
        this.memberService = memberService;
    }

    @KafkaListener(topics = "create-workspace-topic", groupId = "member-service")
    public void listen(String message) {
        String[] parts = message.split(",");
        UUID workspaceId = UUID.fromString(parts[0]);
        UUID ownerId = UUID.fromString(parts[1]);
        String username = parts[2];
        String email = parts[3];
        roleService.createFirstGuestRole(workspaceId, "Guest", "Guest Role Just Can Access Some Resources");
        UUID roleId = roleService.createFirstAdminRole(workspaceId, "Admin", "Admin Role Can Access All Resources");
        memberService.createFirstMember(workspaceId, ownerId, roleId, username, email);
    }
}
