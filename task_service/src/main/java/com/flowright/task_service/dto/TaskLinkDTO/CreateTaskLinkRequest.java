package com.flowright.task_service.dto.TaskLinkDTO;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskLinkRequest {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Link is required")
    private String link;
}
