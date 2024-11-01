package com.flowright.workspace_service.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.flowright.workspace_service.dto.InviteDTO;
import com.flowright.workspace_service.entity.Invite;
import com.flowright.workspace_service.exception.ResourceNotFoundException;
import com.flowright.workspace_service.repository.InviteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InviteService {
    private final InviteRepository inviteRepository;

    public InviteDTO createInvite(InviteDTO inviteDTO) {
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

    public InviteDTO verifyInvite(String email, String otp) {
        Invite invite = inviteRepository
                .findByEmailAndOtp(email, otp)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid invite"));

        if (invite.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new ResourceNotFoundException("Invite has expired");
        }

        if (!"PENDING".equals(invite.getStatus())) {
            throw new ResourceNotFoundException("Invite is not pending");
        }

        invite.setStatus("ACCEPTED");
        invite = inviteRepository.save(invite);
        return convertToDTO(invite);
    }

    private String generateOTP() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    private InviteDTO convertToDTO(Invite invite) {
        return InviteDTO.builder()
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
