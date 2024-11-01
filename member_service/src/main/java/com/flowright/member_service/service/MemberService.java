package com.flowright.member_service.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.flowright.member_service.dto.MemberResponse;
import com.flowright.member_service.dto.UpdateMemberRequest;
import com.flowright.member_service.entity.Member;
import com.flowright.member_service.entity.Role;
import com.flowright.member_service.repository.MemberRepository;
import com.flowright.member_service.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;

    public MemberResponse createMember(Long workspaceId, Long userId) {
        // Check if member already exists
        if (memberRepository.existsByUserIdAndWorkspaceId(userId, workspaceId)) {
            throw new RuntimeException("Member already exists in workspace");
        }

        // Get default role
        Role defaultRole = roleRepository
                .findByWorkspaceIdAndIsDefaultTrue(workspaceId)
                .orElseThrow(() -> new RuntimeException("Default role not found"));

        Member member = Member.builder()
                .userId(userId)
                .workspaceId(workspaceId)
                .role(defaultRole)
                .build();

        Member savedMember = memberRepository.save(member);
        return toMemberResponse(savedMember);
    }

    public MemberResponse createFirstMember(Long workspaceId, Long userId) {
        if (!memberRepository.findByWorkspaceId(workspaceId).isEmpty()) {
            throw new RuntimeException("Workspace already has members");
        }

        Member member = Member.builder()
                .userId(userId)
                .workspaceId(workspaceId)
                .role(null)
                .build();

        Member savedMember = memberRepository.save(member);
        return toMemberResponse(savedMember);
    }

    public MemberResponse updateMember(Long id, UpdateMemberRequest request) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new RuntimeException("Member not found"));

        if (request.getRoleId() != null) {
            Role role = roleRepository
                    .findById(request.getRoleId())
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            member.setRole(role);
        }

        Member updatedMember = memberRepository.save(member);
        return toMemberResponse(updatedMember);
    }

    public void deleteMember(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new RuntimeException("Member not found");
        }
        memberRepository.deleteById(id);
    }

    public MemberResponse getMemberById(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new RuntimeException("Member not found"));
        return toMemberResponse(member);
    }

    public List<MemberResponse> getWorkspaceMembers(Long workspaceId) {
        return memberRepository.findByWorkspaceId(workspaceId).stream()
                .map(this::toMemberResponse)
                .collect(Collectors.toList());
    }

    private MemberResponse toMemberResponse(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .userId(member.getUserId())
                .workspaceId(member.getWorkspaceId())
                .roleId(member.getRole() != null ? member.getRole().getId() : null)
                .build();
    }
}
