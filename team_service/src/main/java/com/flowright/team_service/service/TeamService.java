package com.flowright.team_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flowright.team_service.dto.TeamDTO.CreateTeamRequest;
import com.flowright.team_service.dto.TeamDTO.GetTeamOfMemberWithStatusTaskResponse;
import com.flowright.team_service.dto.TeamDTO.GetTeamOfWorkspaceResponse;
import com.flowright.team_service.entity.Team;
import com.flowright.team_service.entity.TeamMember;
import com.flowright.team_service.kafka.consumer.GetMemberInfoConsumer;
import com.flowright.team_service.kafka.consumer.GetTotalStatusTaskOfTeamOrProjectConsumer;
import com.flowright.team_service.kafka.producer.CreateNotificationProducer;
import com.flowright.team_service.kafka.producer.GetMemberInfoProducer;
import com.flowright.team_service.kafka.producer.GetTotalStatusTaskOfTeamOrProjectProducer;
import com.flowright.team_service.repository.TeamRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamService {
    @Autowired
    private final TeamRepository teamRepository;

    @Autowired
    private final TeamMemberService teamMemberService;

    @Autowired
    private final GetMemberInfoProducer getMemberInfoProducer;

    @Autowired
    private final GetMemberInfoConsumer getMemberInfoConsumer;

    @Autowired
    private final GetTotalStatusTaskOfTeamOrProjectProducer getTotalStatusTaskOfTeamOrProjectProducer;

    @Autowired
    private final GetTotalStatusTaskOfTeamOrProjectConsumer getTotalStatusTaskOfTeamOrProjectConsumer;

    @Autowired
    private final CreateNotificationProducer createNotificationProducer;

    @Autowired
    private final RedisTemplate<String, Object> redisTemplate;

    @Transactional
    public String createTeam(CreateTeamRequest request, UUID workspaceId, UUID creatorId) {
        Team team = Team.builder()
                .leaderId(UUID.fromString(request.getLeaderId()))
                .creatorId(creatorId)
                .name(request.getName())
                .description(request.getDescription())
                .type(request.getType())
                .status("active")
                .workspaceId(workspaceId)
                .build();

        Team savedTeam = teamRepository.save(team);
        createNotificationProducer.sendMessage(
                workspaceId,
                UUID.fromString(request.getLeaderId()),
                "Team Created",
                "Team " + team.getName() + " has been created",
                "/team/management/" + team.getId(),
                "notification");
        teamMemberService.addMemberToTeam(savedTeam.getId(), UUID.fromString(request.getLeaderId()));
        return "Team created successfully";
    }

    public List<GetTeamOfWorkspaceResponse> getAllTeamWorkspace(UUID workspaceId) {
        List<Team> teams = teamRepository.findByWorkspaceId(workspaceId);
        List<GetTeamOfWorkspaceResponse> responses = new ArrayList<>();
        for (Team team : teams) {
            getMemberInfoProducer.sendMessage(team.getLeaderId());
            String getMemberInfoConsumerResponse = getMemberInfoConsumer.getResponse();
            System.out.println(getMemberInfoConsumerResponse);
            String[] responseSplit = getMemberInfoConsumerResponse.split(",");
            String leaderUsername = responseSplit[0];

            int totalMember = teamMemberService.getTotalMemberInTeam(team.getId());

            responses.add(GetTeamOfWorkspaceResponse.builder()
                    .id(team.getId())
                    .name(team.getName())
                    .description(team.getDescription())
                    .leaderId(team.getLeaderId())
                    .leaderUsername(leaderUsername)
                    .totalMember(totalMember)
                    .status(team.getStatus())
                    .build());
        }
        return responses;
    }

    public List<GetTeamOfMemberWithStatusTaskResponse> getMemberTeamWorkspace(UUID memberId) {
        List<TeamMember> teamMembers = teamMemberService.getMemberTeamWorkspace(memberId);
        List<UUID> teamIds = teamMembers.stream().map(TeamMember::getTeamId).collect(Collectors.toList());
        List<GetTeamOfMemberWithStatusTaskResponse> responses = new ArrayList<>();
        for (UUID teamId : teamIds) {
            String redisKey = "team_task_status:" + teamId;

            String cachedResponse = (String) redisTemplate.opsForValue().get(redisKey);
            String[] responseSplit;

            if (cachedResponse == null) {
                getTotalStatusTaskOfTeamOrProjectProducer.sendMessage(teamId, "team");
                String response = getTotalStatusTaskOfTeamOrProjectConsumer.getResponse();

                redisTemplate.opsForValue().set(redisKey, response, 5, TimeUnit.MINUTES);
                responseSplit = response.split(",");
            } else {
                responseSplit = cachedResponse.split(",");
            }

            // getTotalStatusTaskOfTeamOrProjectProducer.sendMessage(teamId, "team");
            // String response = getTotalStatusTaskOfTeamOrProjectConsumer.getResponse();
            // System.out.println(response);
            // String[] responseSplit = response.split(",");

            int totalTodo = Integer.parseInt(responseSplit[0]);
            int totalInprogress = Integer.parseInt(responseSplit[1]);
            int totalOverdue = Integer.parseInt(responseSplit[2]);
            int totalDone = Integer.parseInt(responseSplit[3]);
            int totalOverdone = Integer.parseInt(responseSplit[4]);
            int totalCancel = Integer.parseInt(responseSplit[5]);
            responses.add(GetTeamOfMemberWithStatusTaskResponse.builder()
                    .id(teamId)
                    .name(teamRepository.findById(teamId).get().getName())
                    .description(teamRepository.findById(teamId).get().getDescription())
                    .type(teamRepository.findById(teamId).get().getType())
                    .status(teamRepository.findById(teamId).get().getStatus())
                    .totalTodo(totalTodo)
                    .totalInprogress(totalInprogress)
                    .totalOverdue(totalOverdue)
                    .totalDone(totalDone)
                    .totalOverdone(totalOverdone)
                    .totalCancel(totalCancel)
                    .build());
        }
        return responses;
    }

    public Team getTeamById(UUID teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new RuntimeException("Team not found"));
        return team;
    }
}
