package com.flowright.member_service.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.flowright.member_service.entity.Member;
import com.flowright.member_service.entity.MemberSpecialization;
import com.flowright.member_service.entity.Specialization;
import com.flowright.member_service.repository.MemberRepository;
import com.flowright.member_service.repository.MemberSpecializationRepository;
import com.flowright.member_service.repository.SpecializationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberSpecializationService {
    private final MemberSpecializationRepository memberSpecializationRepository;
    private final MemberRepository memberRepository;
    private final SpecializationRepository specializationRepository;

    public void addSpecializationToMember(Long memberId, Long specializationId, String level, int yearsOfExperience) {
        Member member = memberRepository
                .findById(memberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found"));

        Specialization specialization = specializationRepository
                .findById(specializationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Specialization not found"));

        MemberSpecialization memberSpecialization = new MemberSpecialization();
        memberSpecialization.setMember(member);
        memberSpecialization.setSpecialization(specialization);
        memberSpecialization.setLevel(level);
        memberSpecialization.setYearsOfExperience(yearsOfExperience);

        // Lưu MemberSpecialization
        memberSpecializationRepository.save(memberSpecialization);
    }
}
