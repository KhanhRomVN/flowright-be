package com.flowright.workspace_service.dto.InviteDTO;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateInviteRequest {
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotNull(message = "Role ID cannot be null")
    private UUID roleId;
}
