package com.flowright.member_service.dto.SpecializationDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecializationResponse {
    private Long id;
    private String name;
    private String description;
    private Long workspaceId;
}
