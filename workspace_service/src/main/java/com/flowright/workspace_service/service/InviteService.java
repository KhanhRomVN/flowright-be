package com.flowright.workspace_service.service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.flowright.workspace_service.dto.InviteDTO.CreateInviteRequest;
import com.flowright.workspace_service.dto.InviteDTO.CreateInviteResponse;
import com.flowright.workspace_service.dto.InviteDTO.AcceptInviteRequest;
import com.flowright.workspace_service.dto.InviteDTO.AcceptInviteReponse;
import com.flowright.workspace_service.kafka.producer.CreateMemberWorkspaceProducer;    
import com.flowright.workspace_service.kafka.consumer.CreateMemberWorkspaceConsumer;
import com.flowright.workspace_service.entity.Invite;
import com.flowright.workspace_service.kafka.consumer.CheckMemberWorkspaceConsumer;
import com.flowright.workspace_service.kafka.producer.CheckMemberWorkspaceProducer;
import com.flowright.workspace_service.kafka.producer.GetAccessTokenByWorkspaceIdProducer;
import com.flowright.workspace_service.kafka.consumer.GetAccessTokenByWorkspaceIdConsumer;
import com.flowright.workspace_service.repository.InviteRepository;
import com.flowright.workspace_service.exception.WorkspaceException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InviteService {
    private final InviteRepository inviteRepository;
    private final CreateMemberWorkspaceProducer createMemberWorkspaceProducer;
    private final CreateMemberWorkspaceConsumer createMemberWorkspaceConsumer;
    private final GetAccessTokenByWorkspaceIdProducer getAccessTokenByWorkspaceIdProducer;
    private final GetAccessTokenByWorkspaceIdConsumer getAccessTokenByWorkspaceIdConsumer;
    private final CheckMemberWorkspaceProducer checkMemberWorkspaceProducer;
    private final CheckMemberWorkspaceConsumer checkMemberWorkspaceConsumer;
    public CreateInviteResponse createInvite(UUID workspaceId, CreateInviteRequest request) {
        checkMemberWorkspaceProducer.sendMessage(workspaceId, request.getEmail());  

        String exists = checkMemberWorkspaceConsumer.getResponse();

        if (exists.equals("true")) {
            throw new WorkspaceException("Member already exists in workspace", HttpStatus.BAD_REQUEST);
        }

        String token = generateToken();
        inviteRepository.save(Invite.builder()
                .workspaceId(workspaceId)
                .email(request.getEmail())
                .roleId(request.getRoleId())
                .token(token)
                .status("pending")
                .expiresAt(LocalDateTime.now().plusDays(7))
                .build());

        return CreateInviteResponse.builder()
                .token(token)
                .build();
    }

    private String generateToken() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder token = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            token.append(characters.charAt(random.nextInt(characters.length())));
        }
        return token.toString();
    }   

    public AcceptInviteReponse acceptInvite(AcceptInviteRequest request, UUID userId) {
        Invite invite = inviteRepository.findByTokenAndWorkspaceIdAndEmail(request.getToken(), UUID.fromString(request.getWorkspaceId()), request.getEmail());

        if (invite == null) {
            throw new WorkspaceException("Invite not found", HttpStatus.BAD_REQUEST);
        }

        createMemberWorkspaceProducer.sendMessage(invite.getWorkspaceId().toString(), invite.getEmail(), request.getUsername(), invite.getRoleId().toString());
        String memberId = createMemberWorkspaceConsumer.getMemberId();


        getAccessTokenByWorkspaceIdProducer.sendMessage(userId.toString(), memberId, invite.getWorkspaceId().toString(), invite.getRoleId().toString());
        String accessToken = getAccessTokenByWorkspaceIdConsumer.getAccessToken();
        
        return AcceptInviteReponse.builder()
                .accessToken(accessToken)
                .build();
    }
}
