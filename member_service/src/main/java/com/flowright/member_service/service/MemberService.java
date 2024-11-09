package com.flowright.member_service.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.flowright.member_service.dto.MemberDTO.BasicMemberResponse;
import com.flowright.member_service.dto.MemberDTO.MemberResponse;
import com.flowright.member_service.dto.MemberDTO.SimpleMemberResponse;
import com.flowright.member_service.dto.TokenResponse;
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
    private final JwtService jwtService;
    // private final RoleService roleService;
    // private final MemberSpecializationService memberSpecializationService;
    // private final SpecializationService specializationService;

    public String createMember(UUID userId, UUID workspaceId, String email, String username, UUID roleId) {
        if (memberRepository.existsByEmailAndWorkspaceId(email, workspaceId)) {
            throw new RuntimeException("Member already exists");
        }

        Member member = Member.builder()
                .userId(userId)
                .email(email)
                .username(username)
                .roleId(roleId)
                .workspaceId(workspaceId)
                .build();
        memberRepository.save(member);
        return member.getId().toString();
    }

    public void createFirstMember(UUID workspaceId, UUID userId, UUID roleId, String username, String email) {

        Member member = Member.builder()
                .userId(userId)
                .workspaceId(workspaceId)
                .roleId(roleId)
                .username(username)
                .email(email)
                .build();

        memberRepository.save(member);
    }

    public int getTotalMember(UUID workspaceId) {
        return memberRepository.countByWorkspaceId(workspaceId);
    }

    public MemberResponse getMemberById(UUID id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new RuntimeException("Member not found"));
        return toMemberResponse(member);
    }

    public TokenResponse getNewAccessToken(UUID workspaceId, UUID userId) {
        Member member = memberRepository
                .findByUserIdAndWorkspaceId(userId, workspaceId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        String accessToken = jwtService.generateAccessToken(
                userId.toString(),
                member.getId().toString(),
                workspaceId.toString(),
                member.getRoleId().toString());
        return TokenResponse.builder().access_token(accessToken).build();
    }

    // public List<MemberResponse> getWorkspaceMembers(UUID workspaceId) {
    //     List<Member> memberData = memberRepository.findByWorkspaceId(workspaceId);
    //     List<MemberResponse> members =
    //             memberData.stream().map(this::toMemberResponse).collect(Collectors.toList());

    //     for (MemberResponse member : members) {
    //         RoleResponse role = roleService.getRoleById(member.getRoleId());
    //         String roleName = role.getName();
    //         member.setRoleName(roleName);
    //     }

    //     for (MemberResponse member : members) {
    //         MemberSpecializationResponse memberSpecialization =
    //                 memberSpecializationService.getMemberSpecializationById(member.getId());
    //         member.setLevel(memberSpecialization.getLevel());
    //         member.setYearsOfExperience(memberSpecialization.getYearsOfExperience());
    //         SpecializationResponse specialization =
    //                 specializationService.getSpecializationById(memberSpecialization.getSpecializationId(), true);
    //         member.setSpecializationName(specialization.getName());
    //         member.setIsDefault(memberSpecialization.getIsDefault());
    //     }

    //     return members;
    // }

    public List<SimpleMemberResponse> getSimpleMembersByWorkspaceId(UUID workspaceId) {
        List<Member> members = memberRepository.findByWorkspaceId(workspaceId);
        return members.stream().map(this::toSimpleMemberResponse).collect(Collectors.toList());
    }

    private SimpleMemberResponse toSimpleMemberResponse(Member member) {
        return SimpleMemberResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .username(member.getUsername())
                .build();
    }

    private BasicMemberResponse toBasicMemberResponse(Member member) {
        return BasicMemberResponse.builder()
                .id(member.getId())
                .username(member.getUsername())
                .email(member.getEmail())
                .build();
    }

    public List<BasicMemberResponse> getMembersByRoleId(UUID roleId) {
        List<Member> members = memberRepository.findByRoleId(roleId);
        return members.stream().map(this::toBasicMemberResponse).collect(Collectors.toList());
    }

    private MemberResponse toMemberResponse(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .userId(member.getUserId())
                .workspaceId(member.getWorkspaceId())
                .roleId(member.getRoleId())
                .email(member.getEmail())
                .username(member.getUsername())
                .build();
    }

    public MemberResponse updateMemberRole(UUID memberId, UUID roleId, UUID workspaceId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Role not found"));
        member.setRoleId(role.getId());
        Member updatedMember = memberRepository.save(member);
        return toMemberResponse(updatedMember);
    }

    public boolean checkMemberWorkspace(UUID workspaceId, String email) {
        return memberRepository.existsByWorkspaceIdAndEmail(workspaceId, email);
    }
}
