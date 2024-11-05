package com.flowright.member_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flowright.member_service.dto.MembersSpecializationDTO.AddSpecializationToMemberRequest;
import com.flowright.member_service.service.JwtService;
import com.flowright.member_service.service.MemberSpecializationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/member-service/members-specializations")
@RequiredArgsConstructor
public class MembersSpecializationController {
    private final MemberSpecializationService memberSpecializationService;
    private final JwtService jwtService;

    // Add specialization to member: /member-service/members-specializations
    @PostMapping
    public ResponseEntity<Void> addSpecializationToMember(
            @RequestHeader("access_token") String accessToken, @RequestBody AddSpecializationToMemberRequest request) {
        Long memberId = jwtService.extractMemberId(accessToken);
        memberSpecializationService.addSpecializationToMember(
                memberId,
                request.getSpecializationId(),
                request.getLevel(),
                request.getYearsOfExperience(),
                request.getIsDefault());
        return ResponseEntity.ok().build();
    }
}
