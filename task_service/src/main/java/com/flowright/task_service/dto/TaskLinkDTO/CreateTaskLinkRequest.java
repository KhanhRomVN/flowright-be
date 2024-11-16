package com.flowright.task_service.dto.TaskLinkDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskLinkRequest {
    private String title;
    private String link;
}
