package com.flowright.member_service.dto.MemberDTO;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SimpleMemberResponse {
    private UUID id;
    private String email;
    private String username;
}
