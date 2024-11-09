package com.flowright.member_service.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flowright.member_service.dto.MemberDTO.SimpleMemberResponse;
import com.flowright.member_service.dto.TokenResponse;
import com.flowright.member_service.service.JwtService;
import com.flowright.member_service.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/member-service/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final JwtService jwtService;

    // create member: /member-service/members
    // @PostMapping
    // public ResponseEntity<MemberResponse> createMember(
    //         @Valid @RequestBody CreateMemberRequest request, @RequestHeader("access_token") String token) {
    //     UUID userId = jwtService.extractUserId(token);
    //     return ResponseEntity.ok(memberService.createMember(request.getWorkspaceId(), userId));
    // }

    // get another member by id: /member-service/members/another/{member_id}
    // @GetMapping("/another/{member_id}")
    // public ResponseEntity<MemberResponse> getMemberById(
    //         @PathVariable UUID member_id, @RequestHeader("access_token") String token) {
    //     jwtService.validateToken(token);
    //     return ResponseEntity.ok(memberService.getMemberById(member_id));
    // }

    // get member by id: /member-service/members/member
    // @GetMapping("/member")
    // public ResponseEntity<MemberResponse> getMemberByMemberId(@RequestHeader("access_token") String token) {
    //     jwtService.validateToken(token);
    //     UUID memberId = jwtService.extractMemberId(token);
    //     return ResponseEntity.ok(memberService.getMemberById(memberId));
    // }

    // get workspace members: /member-service/members/workspace
    // @GetMapping("/workspace")
    // public ResponseEntity<List<MemberResponse>> getWorkspaceMembers(@RequestHeader("access_token") String token) {
    //     UUID workspaceId = jwtService.extractWorkspaceId(token);
    //     return ResponseEntity.ok(memberService.getWorkspaceMembers(workspaceId));
    // }

    // get list simple member by workspace_id: /member-service/members/workspace/simple
    @GetMapping("/workspace/simple")
    public ResponseEntity<List<SimpleMemberResponse>> getSimpleMembersByWorkspaceId(
            @RequestHeader("access_token") String token) {
        UUID workspaceId = jwtService.extractWorkspaceId(token);
        return ResponseEntity.ok(memberService.getSimpleMembersByWorkspaceId(workspaceId));
    }

    // get list member by role_id: /member-service/members/role/{role_id}
    // @GetMapping("/role/{role_id}")
    // public ResponseEntity<List<BasicMemberResponse>> getMembersByRoleId(
    //         @PathVariable UUID role_id, @RequestHeader("access_token") String token) {
    //     jwtService.validateToken(token);
    //     String roleId = jwtService.extractRoleId(token);
    //     List<BasicMemberResponse> members = memberService.getMembersByRoleId(roleId);
    //     return ResponseEntity.ok(members);
    // }

    // get new access token: /member-service/members/new/access-token
    @GetMapping("/new/access-token")
    public ResponseEntity<TokenResponse> getNewAccessToken(
            @RequestHeader("access_token") String token, @RequestParam("workspace_id") String workspaceId) {
        UUID userId = jwtService.extractUserId(token);
        UUID workspaceIdUUID = UUID.fromString(workspaceId);
        return ResponseEntity.ok(memberService.getNewAccessToken(workspaceIdUUID, userId));
    }

    // update role_id member: /member-service/members/role/{role_id}/{member_id}
    // @PutMapping("/role/{role_id}/{member_id}")
    // public ResponseEntity<MemberResponse> updateMemberRole(
    //         @PathVariable UUID role_id, @PathVariable UUID member_id, @RequestHeader("access_token") String token) {
    //     UUID workspaceId = jwtService.extractWorkspaceId(token);
    //     return ResponseEntity.ok(memberService.updateMemberRole(member_id, role_id, workspaceId));
    // }
}
