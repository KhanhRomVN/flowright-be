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
public class GetListMemberSpecialization {
    private UUID id;
    private UUID memberId;
    private String username;
    private String email;
    private UUID specializationId;
    private String specializationName;
    private String level;
    private int yearsOfExperience;
    private Boolean isDefault;
}
