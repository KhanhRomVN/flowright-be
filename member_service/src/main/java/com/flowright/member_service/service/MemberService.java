package com.flowright.member_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.flowright.member_service.entity.Member;
import com.flowright.member_service.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    // private final RoleRepository roleRepository;
    // private final JwtService jwtService;

    public Member createMember(Long workspaceId, Long userId) {
        // Check if member already exists
        if (memberRepository.existsByUserIdAndWorkspaceId(userId, workspaceId)) {
            throw new RuntimeException("Member already exists in workspace");
        }

        // Get default role
        // Role defaultRole = roleRepository
        //         .findByWorkspaceIdAndIsDefaultTrue(workspaceId)
        //         .orElseThrow(() -> new RuntimeException("Default role not found"));

        Member member = Member.builder()
                .userId(userId)
                .workspaceId(workspaceId)
                // .role(defaultRole)
                .build();

        return memberRepository.save(member);
    }

    public List<Member> getWorkspaceMembers(Long workspaceId) {
        return memberRepository.findByWorkspaceId(workspaceId);
    }

    // Add other necessary methods
}
