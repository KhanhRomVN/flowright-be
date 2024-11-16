package com.flowright.task_service.dto.TaskCommentDTO;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetTaskCommentResponse {
    private UUID commentId;
    private UUID taskId;
    private UUID memberId;
    private String memberUsername;
    private String memberEmail;
    private String comment;
    private LocalDateTime createdAt;
}
