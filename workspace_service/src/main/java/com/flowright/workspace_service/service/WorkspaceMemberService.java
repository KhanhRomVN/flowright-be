package com.flowright.workspace_service.service;

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
    private GetUserInfoProducer getUserInfoProducer;
    private GetUserInfoConsumer getUserInfoConsumer;
    private GetTotalMemberProducer getTotalMemberProducer;
    private GetTotalMemberConsumer getTotalMemberConsumer;
    private List<GetListWorkspaceMemberReponse> listWorkspaceMemberReponse;
    
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
        getUserInfoProducer.sendMessage(listWorkspaceId.get(0));
        System.out.println("listWorkspaceId: " + listWorkspaceId.get(0));
        // for (UUID workspaceId : listWorkspaceId) {
        //     Workspace workspace = workspaceRepository.findById(workspaceId)
        //             .orElseThrow(() -> new RuntimeException("Workspace not found"));
        //     getUserInfoProducer.sendMessage(workspace.getOwnerId());
        //     String response = getUserInfoConsumer.getResponse();
        //     System.out.print("response: " + response);
        //     String[] responseArray = response.split(",");
        //     String username = responseArray[0];

        //     getTotalMemberProducer.sendMessage(workspaceId);
        //     int totalMembers = getTotalMemberConsumer.getTotalMember();
        //     System.out.println("totalMembers: " + totalMembers);
            

        //     GetListWorkspaceMemberReponse getListWorkspaceMemberReponse = GetListWorkspaceMemberReponse.builder()
        //             .userId(userId)
        //             .workspaceId(workspaceId)
        //             .ownerId(workspace.getOwnerId())
        //             .ownerUsername(username)
        //             .totalMembers(totalMembers)
        //             .build();
        //     listWorkspaceMemberReponse.add(getListWorkspaceMemberReponse);
        // }
        return listWorkspaceMemberReponse;
    }
}
