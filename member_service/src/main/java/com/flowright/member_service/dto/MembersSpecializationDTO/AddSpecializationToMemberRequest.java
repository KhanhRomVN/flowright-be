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
public class AddSpecializationToMemberRequest {
    private UUID specializationId;
    private String level;
    private int yearsOfExperience;
    private Boolean isDefault;
}
