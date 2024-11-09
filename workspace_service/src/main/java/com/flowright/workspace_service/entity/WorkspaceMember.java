package com.flowright.workspace_service.entity;

import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "workspace_members")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceMember {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "workspace_id", nullable = false)
    private UUID workspaceId;

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getWorkspaceId() {
        return workspaceId;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setWorkspaceId(UUID workspaceId) {
        this.workspaceId = workspaceId;
    }
    
}

