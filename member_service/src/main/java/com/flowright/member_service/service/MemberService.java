package com.flowright.member_service.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.flowright.member_service.dto.MemberDTO.BasicMemberResponse;
import com.flowright.member_service.dto.MemberDTO.MemberResponse;
import com.flowright.member_service.dto.MemberDTO.UpdateMemberRequest;
import com.flowright.member_service.dto.MembersSpecializationDTO.MemberSpecializationResponse;
import com.flowright.member_service.dto.RoleDTO.RoleResponse;
import com.flowright.member_service.dto.SpecializationDTO.SpecializationResponse;
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
    private final RoleService roleService;
    private final MemberSpecializationService memberSpecializationService;
    private final SpecializationService specializationService;

    public MemberResponse createMember(Long workspaceId, Long userId) {
        // Check if member already exists
        if (memberRepository.existsByUserIdAndWorkspaceId(userId, workspaceId)) {
            throw new RuntimeException("Member already exists in workspace");
        }

        // Get default role
        Role defaultRole = roleRepository
                .findByWorkspaceIdAndName(workspaceId, "Admin")
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
        List<Member> memberData = memberRepository.findByWorkspaceId(workspaceId);
        List<MemberResponse> members =
                memberData.stream().map(this::toMemberResponse).collect(Collectors.toList());

        for (MemberResponse member : members) {
            RoleResponse role = roleService.getRoleById(member.getRoleId());
            String roleName = role.getName();
            member.setRoleName(roleName);
        }

        for (MemberResponse member : members) {
            MemberSpecializationResponse memberSpecialization =
                    memberSpecializationService.getMemberSpecializationById(member.getId());
            member.setLevel(memberSpecialization.getLevel());
            member.setYearsOfExperience(memberSpecialization.getYearsOfExperience());
            SpecializationResponse specialization =
                    specializationService.getSpecializationById(memberSpecialization.getSpecializationId(), true);
            member.setSpecializationName(specialization.getName());
            member.setIsDefault(memberSpecialization.getIsDefault());
        }

        return members;
    }

    private BasicMemberResponse toBasicMemberResponse(Member member) {
        return BasicMemberResponse.builder()
                .id(member.getId())
                .username(member.getUsername())
                .email(member.getEmail())
                .build();
    }

    public List<BasicMemberResponse> getMembersByRoleId(Long roleId) {
        List<Member> members = memberRepository.findByRoleId(roleId);
        return members.stream().map(this::toBasicMemberResponse).collect(Collectors.toList());
    }

    private MemberResponse toMemberResponse(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .userId(member.getUserId())
                .workspaceId(member.getWorkspaceId())
                .roleId(member.getRole() != null ? member.getRole().getId() : null)
                .email(member.getEmail())
                .username(member.getUsername())
                .build();
    }

    public MemberResponse updateMemberRole(Long memberId, Long roleId, Long workspaceId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Role not found"));
        member.setRole(role);
        Member updatedMember = memberRepository.save(member);
        return toMemberResponse(updatedMember);
    }
}
