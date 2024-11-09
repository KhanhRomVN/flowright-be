package com.flowright.workspace_service.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.flowright.workspace_service.dto.InviteDTO.CreateInviteRequest;
import com.flowright.workspace_service.entity.Invite;
import com.flowright.workspace_service.exception.WorkspaceException;
import com.flowright.workspace_service.repository.InviteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InviteService {
    private final InviteRepository inviteRepository;

    public CreateInviteRequest createInvite(CreateInviteRequest inviteDTO) {
        Invite invite = Invite.builder()
                .email(inviteDTO.getEmail())
                .otp(generateOTP())
                .roleId(inviteDTO.getRoleId())
                .status("PENDING")
                .expiresAt(LocalDateTime.now().plusDays(7))
                .workspaceId(inviteDTO.getWorkspaceId())
                .build();

        invite = inviteRepository.save(invite);
        return convertToDTO(invite);
    }

    public CreateInviteRequest verifyInvite(String email, String otp) {
        Invite invite = inviteRepository
                .findByEmailAndOtp(email, otp)  
                .orElseThrow(() -> new WorkspaceException("Invalid invite", HttpStatus.BAD_REQUEST));

        if (invite.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new WorkspaceException("Invite has expired", HttpStatus.BAD_REQUEST);
        }

        if (!"PENDING".equals(invite.getStatus())) {
            throw new WorkspaceException("Invite is not pending", HttpStatus.BAD_REQUEST);
        }

        invite.setStatus("ACCEPTED");
        invite = inviteRepository.save(invite);
        return convertToDTO(invite);
    }

    private String generateOTP() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    private CreateInviteRequest convertToDTO(Invite invite) {
        return CreateInviteRequest.builder()
                .id(invite.getId())
                .email(invite.getEmail())
                .otp(invite.getOtp())
                .roleId(invite.getRoleId())
                .status(invite.getStatus())
                .workspaceId(invite.getWorkspaceId())
                .expiresAt(invite.getExpiresAt())
                .build();
    }
}
