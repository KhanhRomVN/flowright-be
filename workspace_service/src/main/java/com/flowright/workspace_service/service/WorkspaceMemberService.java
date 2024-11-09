package com.flowright.workspace_service.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import com.flowright.workspace_service.dto.WorkspaceMemberDTO.GetListWorkspaceMemberReponse;
import com.flowright.workspace_service.repository.WorkspaceMemberRepository;
import com.flowright.workspace_service.entity.WorkspaceMember;
import com.flowright.workspace_service.entity.Workspace;
import com.flowright.workspace_service.kafka.producer.GetUserInfoProducer;
import com.flowright.workspace_service.kafka.consumer.GetUserInfoConsumer;
import com.flowright.workspace_service.kafka.producer.GetTotalMemberProducer;
import com.flowright.workspace_service.kafka.consumer.GetTotalMemberConsumer;


@Service
public class WorkspaceMemberService {
    private WorkspaceMemberRepository workspaceMemberRepository;
    private WorkspaceService workspaceService;
    private GetUserInfoProducer getUserInfoProducer;
    private GetUserInfoConsumer getUserInfoConsumer;
    private GetTotalMemberProducer getTotalMemberProducer;
    private GetTotalMemberConsumer getTotalMemberConsumer;
    private List<GetListWorkspaceMemberReponse> listWorkspaceMemberReponse;
    public void createWorkspaceMember(UUID userId, UUID workspaceId) {
        workspaceMemberRepository.save(WorkspaceMember.builder()
                .userId(userId)
                .workspaceId(workspaceId)
                .build());
    }

    public List<GetListWorkspaceMemberReponse> getListMembersWorkspaceByUserId(UUID userId) {
        List<UUID> listWorkspaceId = workspaceMemberRepository.findByUserId(userId).stream()
                .map(workspaceMember -> workspaceMember.getWorkspaceId())
                .collect(Collectors.toList());
        for (UUID workspaceId : listWorkspaceId) {
            Workspace workspace = workspaceService.findWorkspaceById(workspaceId);
            getUserInfoProducer.sendMessage(workspace.getOwnerId());
            String response = getUserInfoConsumer.getResponse();
            String[] responseArray = response.split(",");
            String username = responseArray[0];

            getTotalMemberProducer.sendMessage(workspaceId);
            int totalMembers = getTotalMemberConsumer.getTotalMember();

            GetListWorkspaceMemberReponse getListWorkspaceMemberReponse = GetListWorkspaceMemberReponse.builder()
                    .userId(userId)
                    .workspaceId(workspaceId)
                    .ownerId(workspace.getOwnerId())
                    .ownerUsername(username)
                    .totalMembers(totalMembers)
                    .build();
            listWorkspaceMemberReponse.add(getListWorkspaceMemberReponse);
        }
        return listWorkspaceMemberReponse;
    }
}
