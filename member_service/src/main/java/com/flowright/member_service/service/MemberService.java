package com.flowright.member_service.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.flowright.member_service.dto.MemberDTO.BasicMemberResponse;
import com.flowright.member_service.dto.MemberDTO.MemberResponse;
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
    // private final RoleService roleService;
    // private final MemberSpecializationService memberSpecializationService;
    // private final SpecializationService specializationService;

    public MemberResponse createMember(UUID workspaceId, UUID userId) {
        if (memberRepository.existsByUserIdAndWorkspaceId(userId, workspaceId)) {
            return null;
        }

        Role defaultRole = roleRepository
                .findByWorkspaceIdAndName(workspaceId, "Guest")
                .orElseThrow(() -> new RuntimeException("Default role not found"));

        Member member = Member.builder()
                .userId(userId)
                .workspaceId(workspaceId)
                .roleId(defaultRole.getId())
                .build();

        Member savedMember = memberRepository.save(member);
        return toMemberResponse(savedMember);
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

    public MemberResponse getMemberById(UUID id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new RuntimeException("Member not found"));
        return toMemberResponse(member);
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
}
