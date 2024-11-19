package com.flowright.team_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.flowright.team_service.dto.TeamMemberDTO.DeleteMemberTeamRequest;
import com.flowright.team_service.dto.TeamMemberDTO.GetListMemberTeamResponse;
import com.flowright.team_service.entity.TeamMember;
import com.flowright.team_service.exception.TeamException;
import com.flowright.team_service.kafka.consumer.GetMemberInfoTimerConsumer;
import com.flowright.team_service.kafka.producer.GetMemberInfoTimerProducer;
import com.flowright.team_service.repository.TeamMemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamMemberService {
    @Autowired
    private final TeamMemberRepository teamMemberRepository;

    @Autowired
    private final GetMemberInfoTimerProducer getMemberInfoTimerProducer;

    @Autowired
    private final GetMemberInfoTimerConsumer getMemberInfoTimerConsumer;

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
            try {
                getMemberInfoTimerProducer.sendMessage(teamMember.getMemberId());
                String getMemberInfoConsumerResponse = getMemberInfoTimerConsumer
                        .waitForResponse(teamMember.getMemberId())
                        .get(5, TimeUnit.SECONDS);

                String[] responseSplit = getMemberInfoConsumerResponse.split(",");
                String memberUsername = responseSplit[0];
                String memberEmail = responseSplit[1];

                responses.add(GetListMemberTeamResponse.builder()
                        .id(teamMember.getId())
                        .teamId(teamMember.getTeamId())
                        .memberId(teamMember.getMemberId())
                        .memberUsername(memberUsername)
                        .memberEmail(memberEmail)
                        .build());
            } catch (Exception e) {
                throw new TeamException("Failed to get member information", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return responses;
    }

    public int getTotalMemberInTeam(UUID teamId) {
        return teamMemberRepository.findByTeamId(teamId).size();
    }

    public String deleteMemberFromTeam(DeleteMemberTeamRequest request) {
        return null;
    }
}
