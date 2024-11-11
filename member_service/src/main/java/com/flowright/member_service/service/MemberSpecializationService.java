package com.flowright.member_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.flowright.member_service.dto.MembersSpecializationDTO.AddSpecializationToMemberRequest;
import com.flowright.member_service.dto.MembersSpecializationDTO.GetListMemberSpecialization;
import com.flowright.member_service.entity.Member;
import com.flowright.member_service.entity.MemberSpecialization;
import com.flowright.member_service.entity.Specialization;
import com.flowright.member_service.exception.MemberException;
import com.flowright.member_service.repository.MemberSpecializationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberSpecializationService {
    @Autowired
    private final MemberSpecializationRepository memberSpecializationRepository;

    @Autowired
    private final MemberService memberService;

    @Autowired
    private final SpecializationService specializationService;

    public String addSpecializationToMember(AddSpecializationToMemberRequest request, UUID memberId) {
        // checks to see if the user already has this additional specialization
        if (memberSpecializationRepository
                .findByMemberIdAndSpecializationId(memberId, request.getSpecializationId())
                .isPresent()) {
            throw new MemberException("Specialization already exists for this member", HttpStatus.BAD_REQUEST);
        }

        if (request.getIsDefault() == true) {
            if (memberSpecializationRepository
                    .findByMemberIdAndIsDefault(memberId, true)
                    .isPresent()) {
                return "You cannot create another default specialization because there is already another default specialization";
            }
        }

        memberSpecializationRepository.save(MemberSpecialization.builder()
                .memberId(memberId)
                .specializationId(request.getSpecializationId())
                .level(request.getLevel())
                .yearsOfExperience(request.getYearsOfExperience())
                .isDefault(request.getIsDefault())
                .build());

        return "Specialization added successfully";
    }

    public List<GetListMemberSpecialization> getMembersBySpecializationId(String specializationId) {
        List<MemberSpecialization> memberSpecializations =
                memberSpecializationRepository.findBySpecializationId(UUID.fromString(specializationId));
        List<GetListMemberSpecialization> getListMemberSpecializations = new ArrayList<>();
        for (MemberSpecialization memberSpecialization : memberSpecializations) {
            Member member = memberService.getMemberByUserId(memberSpecialization.getMemberId());
            Specialization specialization =
                    specializationService.getSpecializationById(memberSpecialization.getSpecializationId());
            GetListMemberSpecialization getListMemberSpecialization = GetListMemberSpecialization.builder()
                    .id(memberSpecialization.getId())
                    .memberId(memberSpecialization.getMemberId())
                    .username(member.getUsername())
                    .email(member.getEmail())
                    .specializationId(memberSpecialization.getSpecializationId())
                    .specializationName(specialization.getName())
                    .level(memberSpecialization.getLevel())
                    .yearsOfExperience(memberSpecialization.getYearsOfExperience())
                    .isDefault(memberSpecialization.getIsDefault())
                    .build();
            getListMemberSpecializations.add(getListMemberSpecialization);
        }

        return getListMemberSpecializations;
    }
}
