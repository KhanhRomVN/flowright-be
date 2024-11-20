package com.flowright.member_service.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flowright.member_service.dto.MembersSpecializationDTO.AddSpecializationToMemberRequest;
import com.flowright.member_service.dto.MembersSpecializationDTO.GetListMemberSpecialization;
import com.flowright.member_service.service.JwtService;
import com.flowright.member_service.service.MemberSpecializationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/member/service/members-specializations")
@RequiredArgsConstructor
public class MembersSpecializationController {
    private final MemberSpecializationService memberSpecializationService;
    private final JwtService jwtService;

    // Add specialization to member: /member/service/members-specializations
    @PostMapping
    public ResponseEntity<String> addSpecializationToMember(
            @RequestHeader("access_token") String accessToken, @RequestBody AddSpecializationToMemberRequest request) {
        UUID memberId = jwtService.extractMemberId(accessToken);
        UUID workspaceId = jwtService.extractWorkspaceId(accessToken);
        return ResponseEntity.ok(memberSpecializationService.addSpecializationToMember(request, memberId, workspaceId));
    }

    // Get list member by specialization_id:
    // /member/service/members-specializations/specialization?specialization_id={specializationId}
    @GetMapping("/specialization")
    public ResponseEntity<List<GetListMemberSpecialization>> getMembersBySpecializationId(
            @RequestHeader("access_token") String accessToken,
            @RequestParam("specialization_id") String specializationId) {
        jwtService.validateToken(accessToken);
        return ResponseEntity.ok(memberSpecializationService.getMembersBySpecializationId(specializationId));
    }
}
