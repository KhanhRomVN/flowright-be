package com.flowright.member_service.dto.MemberDTO;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponse {
    private UUID id;
    private UUID userId;
    private UUID workspaceId;
    private UUID roleId;
    private String email;
    private String username;
    private String roleName;
    private String level;
    private Integer yearsOfExperience;
    private String specializationName;
    private Boolean isDefault;
}
