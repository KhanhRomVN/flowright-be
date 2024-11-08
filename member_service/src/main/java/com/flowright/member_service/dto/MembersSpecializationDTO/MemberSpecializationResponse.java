package com.flowright.member_service.dto.MembersSpecializationDTO;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberSpecializationResponse {
    private UUID memberId;
    private UUID specializationId;
    private String level;
    private Integer yearsOfExperience;
    private Boolean isDefault;
}
