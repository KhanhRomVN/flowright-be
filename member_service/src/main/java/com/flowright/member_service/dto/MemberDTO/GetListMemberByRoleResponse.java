package com.flowright.member_service.dto.MemberDTO;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetListMemberByRoleResponse {
    private UUID id;
    private String username;
    private String email;
}
