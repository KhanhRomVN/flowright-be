package com.flowright.member_service.dto.RolePermissionDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePermissionRequest {
    private String name;
    private String description;
}
