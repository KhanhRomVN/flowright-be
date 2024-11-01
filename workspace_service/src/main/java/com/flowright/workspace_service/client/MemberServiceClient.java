package com.flowright.workspace_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.bind.annotation.RequestHeader;

import com.flowright.workspace_service.client.dto.CreateRoleRequest;
import com.flowright.workspace_service.client.dto.RoleResponse;
import com.flowright.workspace_service.client.dto.CreateMemberRequest;
import com.flowright.workspace_service.client.dto.MemberResponse;


@FeignClient(name = "member-service", url = "${member-service.url}")
public interface MemberServiceClient {
    @PostMapping("/member-service/roles")
    RoleResponse createRole(@RequestBody CreateRoleRequest request, @RequestHeader("access_token") String token);

    @PostMapping("/member-service/members/first")
    MemberResponse createFirstMember(
            @RequestBody CreateMemberRequest request, @RequestHeader("access_token") String token);
}
