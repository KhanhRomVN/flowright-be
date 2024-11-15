package com.flowright.workspace_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.flowright.workspace_service.dto.WorkspaceMemberDTO.GetListWorkspaceMemberReponse;
import com.flowright.workspace_service.entity.Workspace;
import com.flowright.workspace_service.entity.WorkspaceMember;
import com.flowright.workspace_service.exception.WorkspaceException;
import com.flowright.workspace_service.kafka.consumer.GetTotalMemberConsumer;
import com.flowright.workspace_service.kafka.consumer.GetUserInfoConsumer;
import com.flowright.workspace_service.kafka.producer.GetTotalMemberProducer;
import com.flowright.workspace_service.kafka.producer.GetUserInfoProducer;
import com.flowright.workspace_service.repository.WorkspaceMemberRepository;
import com.flowright.workspace_service.repository.WorkspaceRepository;

@Service
public class WorkspaceMemberService {
    @Autowired
    private WorkspaceMemberRepository workspaceMemberRepository;

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private GetUserInfoProducer getUserInfoProducer;

    @Autowired
    private GetUserInfoConsumer getUserInfoConsumer;

    @Autowired
    private GetTotalMemberProducer getTotalMemberProducer;

    @Autowired
    private GetTotalMemberConsumer getTotalMemberConsumer;

    public void createWorkspaceMember(UUID userId, UUID workspaceId) {
        if (workspaceMemberRepository.findByUserIdAndWorkspaceId(userId, workspaceId) != null) {
            throw new WorkspaceException("User already in workspace", HttpStatus.BAD_REQUEST);
        }
        WorkspaceMember workspaceMember = WorkspaceMember.builder()
                .userId(userId)
                .workspaceId(workspaceId)
                .build();

        workspaceMemberRepository.save(workspaceMember);
    }

    public List<GetListWorkspaceMemberReponse> getListMembersWorkspaceByUserId(UUID userId) {
        List<UUID> listWorkspaceId = workspaceMemberRepository.findByUserId(userId).stream()
                .map(workspaceMember -> workspaceMember.getWorkspaceId())
                .collect(Collectors.toList());
        List<GetListWorkspaceMemberReponse> listWorkspaceMembers = new ArrayList<>();
        for (UUID workspaceId : listWorkspaceId) {
            Workspace workspace = workspaceRepository
                    .findById(workspaceId)
                    .orElseThrow(() -> new WorkspaceException("Workspace not found", HttpStatus.NOT_FOUND));
            getUserInfoProducer.sendMessage(workspace.getOwnerId());
            String message = getUserInfoConsumer.getResponse();
            String ownerUsername = message.split(",")[0];

            getTotalMemberProducer.sendMessage(workspaceId);
            int totalMembers = getTotalMemberConsumer.getTotalMember();

            GetListWorkspaceMemberReponse workspaceMember = GetListWorkspaceMemberReponse.builder()
                    .id(workspace.getId())
                    .name(workspace.getName())
                    .ownerId(workspace.getOwnerId())
                    .ownerUsername(ownerUsername)
                    .totalMembers(totalMembers)
                    .build();
            listWorkspaceMembers.add(workspaceMember);
        }
        return listWorkspaceMembers;
    }
}
