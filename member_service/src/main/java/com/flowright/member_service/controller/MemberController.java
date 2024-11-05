package com.flowright.member_service.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flowright.member_service.dto.MemberDTO.BasicMemberResponse;
import com.flowright.member_service.dto.MemberDTO.CreateMemberRequest;
import com.flowright.member_service.dto.MemberDTO.MemberResponse;
import com.flowright.member_service.dto.TokenResponse;
import com.flowright.member_service.entity.Member;
import com.flowright.member_service.exception.MemberException;
import com.flowright.member_service.repository.MemberRepository;
import com.flowright.member_service.service.JwtService;
import com.flowright.member_service.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/member-service/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    // create member: /member-service/members
    @PostMapping
    public ResponseEntity<MemberResponse> createMember(
            @Valid @RequestBody CreateMemberRequest request, @RequestHeader("access_token") String token) {
        Long userId = jwtService.extractUserId(token);
        return ResponseEntity.ok(memberService.createMember(request.getWorkspaceId(), userId));
    }

    // get another member by id: /member-service/members/another/{member_id}
    @GetMapping("/another/{member_id}")
    public ResponseEntity<MemberResponse> getMemberById(
            @PathVariable Long member_id, @RequestHeader("access_token") String token) {
        jwtService.validateToken(token);
        return ResponseEntity.ok(memberService.getMemberById(member_id));
    }

    // get member by id: /member-service/members/member
    @GetMapping("/member")
    public ResponseEntity<MemberResponse> getMemberByMemberId(@RequestHeader("access_token") String token) {
        jwtService.validateToken(token);
        Long memberId = jwtService.extractMemberId(token);
        return ResponseEntity.ok(memberService.getMemberById(memberId));
    }

    // get workspace members: /member-service/members/workspace
    @GetMapping("/workspace")
    public ResponseEntity<List<MemberResponse>> getWorkspaceMembers(@RequestHeader("access_token") String token) {
        Long workspaceId = jwtService.extractWorkspaceId(token);
        return ResponseEntity.ok(memberService.getWorkspaceMembers(workspaceId));
    }

    // get list member by role_id: /member-service/members/role/{role_id}
    @GetMapping("/role/{role_id}")
    public ResponseEntity<List<BasicMemberResponse>> getMembersByRoleId(
            @PathVariable Long role_id, @RequestHeader("access_token") String token) {
        jwtService.validateToken(token);
        List<BasicMemberResponse> members = memberService.getMembersByRoleId(role_id);
        return ResponseEntity.ok(members);
    }

    // get new access token: /member-service/members/new-access-token/{workspace_id}
    @GetMapping("/new-access-token/{workspace_id}")
    public ResponseEntity<TokenResponse> getNewAccessToken(
            @RequestHeader("access_token") String accessToken, @PathVariable("workspace_id") Long workspaceId) {
        Long userId = jwtService.extractUserId(accessToken);
        Member member = memberRepository
                .findByUserIdAndWorkspaceId(userId, workspaceId)
                .orElseThrow(() -> new MemberException("Member not found", HttpStatus.NOT_FOUND));

        if (member.getUsername() == null || member.getEmail() == null) {
            throw new MemberException("Member data is incomplete", HttpStatus.BAD_REQUEST);
        }

        Long roleId = member.getRole() != null ? member.getRole().getId() : null;
        String newAccessToken = jwtService.generateAccessToken(userId, member.getId(), workspaceId, roleId);

        TokenResponse response =
                TokenResponse.builder().access_token(newAccessToken).build();

        return ResponseEntity.ok(response);
    }

    // update role_id member: /member-service/members/role/{role_id}/{member_id}
    @PutMapping("/role/{role_id}/{member_id}")
    public ResponseEntity<MemberResponse> updateMemberRole(
            @PathVariable Long role_id, @PathVariable Long member_id, @RequestHeader("access_token") String token) {
        Long workspaceId = jwtService.extractWorkspaceId(token);
        return ResponseEntity.ok(memberService.updateMemberRole(member_id, role_id, workspaceId));
    }
}
