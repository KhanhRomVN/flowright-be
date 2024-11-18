package com.flowright.team_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flowright.team_service.dto.TeamDTO.CreateTeamRequest;
import com.flowright.team_service.dto.TeamDTO.GetTeamOfWorkspaceResponse;
import com.flowright.team_service.entity.Team;
import com.flowright.team_service.entity.TeamMember;
import com.flowright.team_service.kafka.consumer.GetMemberInfoConsumer;
import com.flowright.team_service.kafka.producer.GetMemberInfoProducer;
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

    @Transactional
    public String createTeam(CreateTeamRequest request, UUID workspaceId) {
        Team team = Team.builder()
                .leaderId(UUID.fromString(request.getLeaderId()))
                .name(request.getName())
                .description(request.getDescription())
                .type(request.getType())
                .status("active")
                .workspaceId(workspaceId)
                .build();

        Team savedTeam = teamRepository.save(team);
        teamMemberService.addMemberToTeam(savedTeam.getId(), UUID.fromString(request.getLeaderId()));
        return "Team created successfully";
    }

    public List<GetTeamOfWorkspaceResponse> getAllTeamWorkspace(UUID workspaceId) {
        List<Team> teams = teamRepository.findByWorkspaceId(workspaceId);
        List<GetTeamOfWorkspaceResponse> responses = new ArrayList<>();
        for (Team team : teams) {
            getMemberInfoProducer.sendMessage(team.getLeaderId());
            String getMemberInfoConsumerResponse = getMemberInfoConsumer.getResponse();
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

    public List<Team> getMemberTeamWorkspace(UUID memberId) {
        List<TeamMember> teamMembers = teamMemberService.getMemberTeamWorkspace(memberId);
        List<UUID> teamIds = teamMembers.stream().map(TeamMember::getTeamId).collect(Collectors.toList());
        return teamRepository.findAllById(teamIds);
    }
}
