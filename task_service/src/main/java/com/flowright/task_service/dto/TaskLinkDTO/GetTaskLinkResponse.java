package com.flowright.task_service.dto.TaskLinkDTO;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetTaskLinkResponse {
    private UUID taskLinkId;
    private UUID taskId;
    private String title;
    private String link;
}
