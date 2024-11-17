package com.flowright.workspace_service.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flowright.workspace_service.dto.InviteDTO.AcceptInviteReponse;
import com.flowright.workspace_service.dto.InviteDTO.AcceptInviteRequest;
import com.flowright.workspace_service.dto.InviteDTO.CreateInviteRequest;
import com.flowright.workspace_service.dto.InviteDTO.CreateInviteResponse;
import com.flowright.workspace_service.dto.InviteDTO.GetInviteResponse;
import com.flowright.workspace_service.dto.InviteDTO.GetMyInviteResponse;
import com.flowright.workspace_service.entity.Invite;
import com.flowright.workspace_service.entity.Workspace;
import com.flowright.workspace_service.exception.WorkspaceException;
import com.flowright.workspace_service.kafka.consumer.CheckMemberWorkspaceConsumer;
import com.flowright.workspace_service.kafka.consumer.CreateMemberWorkspaceConsumer;
import com.flowright.workspace_service.kafka.consumer.GetAccessTokenByWorkspaceIdConsumer;
import com.flowright.workspace_service.kafka.consumer.GetRoleInfoConsumer;
import com.flowright.workspace_service.kafka.consumer.GetUserInfoConsumer;
import com.flowright.workspace_service.kafka.producer.CheckMemberWorkspaceProducer;
import com.flowright.workspace_service.kafka.producer.CreateMemberWorkspaceProducer;
import com.flowright.workspace_service.kafka.producer.GetAccessTokenByWorkspaceIdProducer;
import com.flowright.workspace_service.kafka.producer.GetRoleInfoProducer;
import com.flowright.workspace_service.kafka.producer.GetUserInfoProducer;
import com.flowright.workspace_service.repository.InviteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InviteService {
    @Autowired
    private final InviteRepository inviteRepository;

    @Autowired
    private final GetRoleInfoProducer getRoleInfoProducer;

    @Autowired
    private final GetRoleInfoConsumer getRoleInfoConsumer;

    @Autowired
    private final CreateMemberWorkspaceProducer createMemberWorkspaceProducer;

    @Autowired
    private final CreateMemberWorkspaceConsumer createMemberWorkspaceConsumer;

    @Autowired
    private final GetAccessTokenByWorkspaceIdProducer getAccessTokenByWorkspaceIdProducer;

    @Autowired
    private final GetAccessTokenByWorkspaceIdConsumer getAccessTokenByWorkspaceIdConsumer;

    @Autowired
    private final CheckMemberWorkspaceProducer checkMemberWorkspaceProducer;

    @Autowired
    private final CheckMemberWorkspaceConsumer checkMemberWorkspaceConsumer;

    @Autowired
    private final WorkspaceMemberService workspaceMemberService;

    @Autowired
    private final MailService mailService;

    @Autowired
    private final GetUserInfoProducer getUserInfoProducer;

    @Autowired
    private final GetUserInfoConsumer getUserInfoConsumer;

    @Autowired
    private final WorkspaceService workspaceService;

    public CreateInviteResponse createInvite(UUID workspaceId, CreateInviteRequest request) {
        if (inviteRepository.findByWorkspaceIdAndEmail(workspaceId, request.getEmail()) != null) {
            inviteRepository.deleteInviteByWorkspaceIdAndEmail(workspaceId, request.getEmail());
        }

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

        mailService.sendEmail(
                request.getEmail(),
                "Invite to workspace",
                "You are invited to workspace " + workspaceId + " with token " + token);

        return CreateInviteResponse.builder().token(token).build();
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
        Invite invite = inviteRepository.findByTokenAndWorkspaceIdAndEmail(
                request.getToken(), UUID.fromString(request.getWorkspaceId()), request.getEmail());

        if (invite == null) {
            throw new WorkspaceException("Invite not found", HttpStatus.BAD_REQUEST);
        }

        createMemberWorkspaceProducer.sendMessage(
                userId.toString(),
                invite.getWorkspaceId().toString(),
                invite.getEmail(),
                request.getUsername(),
                invite.getRoleId().toString());
        String memberId = createMemberWorkspaceConsumer.getMemberId();
        if (memberId == null) {
            throw new WorkspaceException("Member not created", HttpStatus.BAD_REQUEST);
        }

        getAccessTokenByWorkspaceIdProducer.sendMessage(
                userId.toString(),
                memberId,
                invite.getWorkspaceId().toString(),
                invite.getRoleId().toString());
        String accessToken = getAccessTokenByWorkspaceIdConsumer.getAccessToken();
        if (accessToken == null) {
            throw new WorkspaceException("Access token not created", HttpStatus.BAD_REQUEST);
        }

        workspaceMemberService.createWorkspaceMember(userId, invite.getWorkspaceId());

        if (invite.getId() != null) {
            inviteRepository.deleteInviteByEmailAndToken(invite.getEmail(), invite.getToken());
        }

        return AcceptInviteReponse.builder()
                .accessToken(accessToken)
                .inviteId(invite.getId())
                .build();
    }

    public List<GetInviteResponse> getListInvite(UUID workspaceId) {
        List<Invite> invites = inviteRepository.findByWorkspaceId(workspaceId);
        List<GetInviteResponse> getInviteResponses = new ArrayList<>();
        for (Invite invite : invites) {
            getRoleInfoProducer.sendMessage(invite.getRoleId().toString());
            String roleName = getRoleInfoConsumer.getResponse();
            GetInviteResponse getInviteResponse = GetInviteResponse.builder()
                    .id(invite.getId())
                    .email(invite.getEmail())
                    .roleId(invite.getRoleId())
                    .roleName(roleName)
                    .token(invite.getToken())
                    .status(invite.getStatus())
                    .expiresAt(invite.getExpiresAt())
                    .build();
            getInviteResponses.add(getInviteResponse);
        }
        return getInviteResponses;
    }

    @Scheduled(fixedRate = 60000)
    public void changeStatusInviteSchedule() {
        List<Invite> pendingInvites = inviteRepository.findByStatusAndExpiresAtBefore("pending", LocalDateTime.now());
        for (Invite invite : pendingInvites) {
            invite.setStatus("expired");
            inviteRepository.save(invite);
        }
    }

    @Transactional
    public void deleteInvite(UUID id) {
        inviteRepository.deleteInviteById(id);
    }

    public List<GetMyInviteResponse> getMyInvite(UUID userId) {
        getUserInfoProducer.sendMessage(userId);
        String getUserInfoConsumerResponse = getUserInfoConsumer.getResponse();
        String[] responseSplit = getUserInfoConsumerResponse.split(",");
        String email = responseSplit[1];

        List<Invite> invites = inviteRepository.findByEmail(email);
        Workspace workspace = workspaceService.findWorkspaceById(invites.get(0).getWorkspaceId());
        List<GetMyInviteResponse> getMyInviteResponses = new ArrayList<>();
        for (Invite invite : invites) {
            getRoleInfoProducer.sendMessage(invite.getRoleId().toString());
            String roleName = getRoleInfoConsumer.getResponse();
            GetMyInviteResponse getMyInviteResponse = GetMyInviteResponse.builder()
                    .id(invite.getId())
                    .email(invite.getEmail())
                    .roleId(invite.getRoleId())
                    .roleName(roleName)
                    .workspaceId(workspace.getId())
                    .workspaceName(workspace.getName())
                    .token(invite.getToken())
                    .status(invite.getStatus())
                    .expiresAt(invite.getExpiresAt())
                    .build();
            getMyInviteResponses.add(getMyInviteResponse);
        }
        return getMyInviteResponses;
    }
}
