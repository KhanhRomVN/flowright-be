package com.flowright.project_service.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.flowright.project_service.dto.ProjectDTO.CreateProjectRequest;
import com.flowright.project_service.dto.ProjectDTO.CreateProjectResponse;
import com.flowright.project_service.dto.ProjectDTO.GetAllProjectsResponse;
import com.flowright.project_service.entity.Project;
import com.flowright.project_service.exception.ProjectException;
import com.flowright.project_service.kafka.consumer.GetAllProjectIdByTeamIdInTaskConsumer;
import com.flowright.project_service.kafka.consumer.GetAllTeamIdByMemberIdConsumer;
import com.flowright.project_service.kafka.consumer.GetMemberInfoConsumer;
import com.flowright.project_service.kafka.producer.GetAllProjectIdByTeamIdInTaskProducer;
import com.flowright.project_service.kafka.producer.GetAllTeamIdByMemberIdProducer;
import com.flowright.project_service.kafka.producer.GetMemberInfoProducer;
import com.flowright.project_service.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {
    @Autowired
    private final ProjectRepository projectRepository;

    @Autowired
    private final GetMemberInfoConsumer getMemberInfoConsumer;

    @Autowired
    private final GetMemberInfoProducer getMemberInfoProducer;

    @Autowired
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    private final GetAllTeamIdByMemberIdConsumer getAllTeamIdByMemberIdConsumer;

    @Autowired
    private final GetAllTeamIdByMemberIdProducer getAllTeamIdByMemberIdProducer;

    @Autowired
    private final GetAllProjectIdByTeamIdInTaskConsumer getAllProjectIdByTeamIdInTaskConsumer;

    @Autowired
    private final GetAllProjectIdByTeamIdInTaskProducer getAllProjectIdByTeamIdInTaskProducer;

    public CreateProjectResponse createProject(CreateProjectRequest request, UUID workspaceId, UUID creatorId) {
        Project project = Project.builder()
                .name(request.getName())
                .description(request.getDescription())
                .workspaceId(workspaceId)
                .ownerId(UUID.fromString(request.getOwnerId()))
                .creatorId(creatorId)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .status("in_progress")
                .build();

        projectRepository.save(project);

        return CreateProjectResponse.builder()
                .message("Project created successfully")
                .build();
    }

    private String getMemberInfoFromCache(UUID memberId) {
        String memberKey = "member:" + memberId.toString();
        String memberInfo = (String) redisTemplate.opsForValue().get(memberKey);

        if (memberInfo == null) {
            try {
                getMemberInfoProducer.sendMessage(memberId);
                String response = getMemberInfoConsumer.getResponse();

                // Cache the response with 1 hour expiration
                redisTemplate.opsForValue().set(memberKey, response, 1, TimeUnit.HOURS);
                return response;
            } catch (Exception e) {
                throw new ProjectException("Failed to get member information", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return memberInfo;
    }

    public List<GetAllProjectsResponse> getProjectsOfWorkspace(UUID workspaceId) {
        List<Project> projects = projectRepository.findByWorkspaceId(workspaceId);
        List<GetAllProjectsResponse> response = new ArrayList<>();
        for (Project project : projects) {
            String ownerInfo = getMemberInfoFromCache(project.getOwnerId());
            System.out.println(ownerInfo);
            String[] responseSplit = ownerInfo.split(",");
            String ownerUsername = responseSplit[0];

            String creatorInfo = getMemberInfoFromCache(project.getCreatorId());
            System.out.println(creatorInfo);
            responseSplit = creatorInfo.split(",");
            String creatorUsername = responseSplit[0];

            response.add(GetAllProjectsResponse.builder()
                    .id(project.getId())
                    .name(project.getName())
                    .description(project.getDescription())
                    .ownerId(project.getOwnerId())
                    .ownerUsername(ownerUsername)
                    .creatorId(project.getCreatorId())
                    .creatorUsername(creatorUsername)
                    .startDate(project.getStartDate())
                    .endDate(project.getEndDate())
                    .status(project.getStatus())
                    .build());
        }
        return response;
    }

    public Project getProjectById(UUID projectId) {
        return projectRepository.findById(projectId).get();
    }

    public List<GetAllProjectsResponse> getProjectsOfMember(UUID memberId) {
        getAllTeamIdByMemberIdProducer.sendMessage(memberId);
        String response = getAllTeamIdByMemberIdConsumer.getResponse();
        List<UUID> teamIds =
                Arrays.stream(response.split(",")).map(UUID::fromString).collect(Collectors.toList());

        List<GetAllProjectsResponse> result = new ArrayList<>();
        for (UUID teamId : teamIds) {
            getAllProjectIdByTeamIdInTaskProducer.sendMessage(teamId);
            String responseProjectIds = getAllProjectIdByTeamIdInTaskConsumer.getResponse();
            if (responseProjectIds != null && !responseProjectIds.trim().isEmpty()) {
                List<UUID> projectIds = Arrays.stream(responseProjectIds.split(","))
                        .filter(str -> !str.trim().isEmpty()) // Filter out empty strings
                        .map(UUID::fromString)
                        .collect(Collectors.toList());

                for (UUID projectId : projectIds) {
                    Project project = getProjectById(projectId);
                    String ownerInfo = getMemberInfoFromCache(project.getOwnerId());
                    String[] responseSplit = ownerInfo.split(",");
                    String ownerUsername = responseSplit[0];

                    String creatorInfo = getMemberInfoFromCache(project.getCreatorId());
                    responseSplit = creatorInfo.split(",");
                    String creatorUsername = responseSplit[0];

                    result.add(GetAllProjectsResponse.builder()
                            .id(project.getId())
                            .name(project.getName())
                            .description(project.getDescription())
                            .ownerId(project.getOwnerId())
                            .ownerUsername(ownerUsername)
                            .creatorId(project.getCreatorId())
                            .creatorUsername(creatorUsername)
                            .startDate(project.getStartDate())
                            .endDate(project.getEndDate())
                            .status(project.getStatus())
                            .build());
                }
            }
        }

        return result;
    }
}
