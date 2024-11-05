package com.flowright.member_service.dto.MembersSpecializationDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberSpecializationResponse {
    private Long memberId;
    private Long specializationId;
    private String level;
    private Integer yearsOfExperience;
}
