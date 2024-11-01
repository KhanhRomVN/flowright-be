package com.flowright.member_service.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.flowright.member_service.dto.CreateMemberRequest;
import com.flowright.member_service.dto.MemberResponse;
import com.flowright.member_service.dto.UpdateMemberRequest;
import com.flowright.member_service.service.JwtService;
import com.flowright.member_service.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final JwtService jwtService;

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
        Long userId = jwtService.extractUserId(token);
        return ResponseEntity.ok(memberService.updateMember(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id, @RequestHeader("access_token") String token) {
        Long userId = jwtService.extractUserId(token);
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getMemberById(
            @PathVariable Long id, @RequestHeader("access_token") String token) {
        Long userId = jwtService.extractUserId(token);
        return ResponseEntity.ok(memberService.getMemberById(id));
    }

    @GetMapping("/workspace/{workspaceId}")
    public ResponseEntity<List<MemberResponse>> getWorkspaceMembers(
            @PathVariable Long workspaceId, @RequestHeader("access_token") String token) {
        Long userId = jwtService.extractUserId(token);
        return ResponseEntity.ok(memberService.getWorkspaceMembers(workspaceId));
    }

    @PostMapping("/first")
    public ResponseEntity<MemberResponse> createFirstMember(
            @Valid @RequestBody CreateMemberRequest request, @RequestHeader("access_token") String token) {
        Long userId = jwtService.extractUserId(token);
        return ResponseEntity.ok(memberService.createFirstMember(request.getWorkspaceId(), userId));
    }
}
