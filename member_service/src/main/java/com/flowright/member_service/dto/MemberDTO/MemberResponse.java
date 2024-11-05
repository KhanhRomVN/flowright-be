package com.flowright.member_service.dto.MemberDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponse {
    private Long id;
    private Long userId;
    private Long workspaceId;
    private Long roleId;
    private String email;
    private String username;
    private String roleName;
    private String level;
    private Integer yearsOfExperience;
    private String specializationName;
    private Boolean isDefault;
}
