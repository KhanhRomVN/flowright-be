package com.flowright.member_service.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flowright.member_service.dto.CreateMemberRequest;
import com.flowright.member_service.dto.MemberResponse;
import com.flowright.member_service.dto.TokenResponse;
import com.flowright.member_service.dto.UpdateMemberRequest;
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

    @PostMapping
    public ResponseEntity<MemberResponse> createMember(
            @Valid @RequestBody CreateMemberRequest request, @RequestHeader("access_token") String token) {
        Long userId = jwtService.extractUserId(token);
        return ResponseEntity.ok(memberService.createMember(request.getWorkspaceId(), userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberResponse> updateMember(
            @PathVariable Long id,
            @RequestBody UpdateMemberRequest request,
            @RequestHeader("access_token") String token) {
        jwtService.validateToken(token);
        return ResponseEntity.ok(memberService.updateMember(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id, @RequestHeader("access_token") String token) {
        jwtService.validateToken(token);
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getMemberById(
            @PathVariable Long id, @RequestHeader("access_token") String token) {
        jwtService.validateToken(token);
        return ResponseEntity.ok(memberService.getMemberById(id));
    }

    @GetMapping("/workspace/{workspaceId}")
    public ResponseEntity<List<MemberResponse>> getWorkspaceMembers(
            @PathVariable Long workspaceId, @RequestHeader("access_token") String token) {
        jwtService.validateToken(token);
        return ResponseEntity.ok(memberService.getWorkspaceMembers(workspaceId));
    }

    // create first member: /member-service/members/first/{workspaceId}
    @PostMapping("/first/{workspaceId}")
    public ResponseEntity<MemberResponse> createFirstMember(
            @PathVariable Long workspaceId, @RequestHeader("access_token") String token) {
        Long userId = jwtService.extractUserId(token);
        return ResponseEntity.ok(memberService.createFirstMember(workspaceId, userId));
    }

    // /member-service/members/new-access-token/{workspace_id}
    // get access_token (payload: {userId, member_id, workspace_id, role_id}) by access_token (payload: {userId})
    @GetMapping("/new-access-token/{workspace_id}")
    public ResponseEntity<TokenResponse> getNewAccessToken(
            @RequestHeader("access_token") String accessToken, @PathVariable("workspace_id") Long workspaceId) {
        try {
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
        } catch (Exception e) {
            if (e instanceof MemberException) {
                throw e;
            }
            throw new MemberException(
                    "Error generating access token: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
