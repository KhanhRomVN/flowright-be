package com.flowright.team_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flowright.team_service.dto.TeamMemberDTO.GetListMemberTeamResponse;
import com.flowright.team_service.entity.TeamMember;
import com.flowright.team_service.kafka.consumer.GetMemberInfoConsumer;
import com.flowright.team_service.kafka.producer.GetMemberInfoProducer;
import com.flowright.team_service.repository.TeamMemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamMemberService {
    @Autowired
    private final TeamMemberRepository teamMemberRepository;

    @Autowired
    private final GetMemberInfoProducer getMemberInfoProducer;

    private final GetMemberInfoConsumer getMemberInfoConsumer;

    public String addMemberToTeam(UUID teamId, UUID memberId) {
        TeamMember teamMember =
                TeamMember.builder().teamId(teamId).memberId(memberId).build();
        teamMemberRepository.save(teamMember);
        return "Member added to team successfully";
    }

    public List<TeamMember> getMemberTeamWorkspace(UUID memberId) {
        return teamMemberRepository.findByMemberId(memberId);
    }

    public List<GetListMemberTeamResponse> getAllMemberInTeam(UUID teamId) {
        List<TeamMember> teamMembers = teamMemberRepository.findByTeamId(teamId);
        List<GetListMemberTeamResponse> responses = new ArrayList<>();
        for (TeamMember teamMember : teamMembers) {
            getMemberInfoProducer.sendMessage(teamMember.getMemberId());
            String getMemberInfoConsumerResponse = getMemberInfoConsumer.getResponse();
            String[] responseSplit = getMemberInfoConsumerResponse.split(",");
            String assigneeUsername = responseSplit[0];
            String assigneeEmail = responseSplit[1];

            responses.add(GetListMemberTeamResponse.builder()
                    .id(teamMember.getId())
                    .teamId(teamMember.getTeamId())
                    .memberId(teamMember.getMemberId())
                    .memberUsername(assigneeUsername)
                    .memberEmail(assigneeEmail)
                    .build());
        }
        return responses;
    }
}
