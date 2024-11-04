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
                memberId, request.getSpecializationId(), request.getLevel(), request.getYearsOfExperience());
        return ResponseEntity.ok().build();
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<MemberSpecializationResponse> getMemberSpecialization(
    //         @RequestHeader("access_token") String accessToken, @PathVariable Long id) {
    //     return ResponseEntity.ok(memberSpecializationService.getMemberSpecialization(id));
    // }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> removeSpecializationFromMember(
    //         @RequestHeader("access_token") String accessToken, @PathVariable Long id) {
    //     // Logic to remove specialization
    //     return ResponseEntity.noContent().build();
    // }

    // @PutMapping("/{id}")
    // public ResponseEntity<Void> updateLevelAndYearsOfExperience(
    //         @RequestHeader("access_token") String accessToken,
    //         @PathVariable Long id,
    //         @RequestBody UpdateMemberSpecializationRequest request) {
    //     // Logic to update level and years of experience
    //     return ResponseEntity.ok().build();
    // }

    // @PutMapping("/{id}/years-of-experience")
    // public ResponseEntity<Void> updateYearsOfExperience(
    //         @RequestHeader("access_token") String accessToken,
    //         @PathVariable Long id,
    //         @RequestBody UpdateMemberSpecializationRequest request) {
    //     // Logic to update years of experience
    //     return ResponseEntity.ok().build();
    // }
}
