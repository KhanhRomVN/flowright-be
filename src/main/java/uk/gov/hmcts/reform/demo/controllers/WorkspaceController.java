package uk.gov.hmcts.reform.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.reform.demo.dto.WorkspaceRequest;
import uk.gov.hmcts.reform.demo.dto.WorkspaceResponse;
import uk.gov.hmcts.reform.demo.entity.Workspace;
import uk.gov.hmcts.reform.demo.repository.WorkspaceRepository;
import uk.gov.hmcts.reform.demo.util.JwtUtil;

@RestController
public class WorkspaceController {

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/workspace")
    public ResponseEntity<WorkspaceResponse> createWorkspace(
            @RequestHeader("access_token") String token,
            @RequestBody WorkspaceRequest request) {

        if (token == null || token.isEmpty()) {
            throw new RuntimeException("Token is required");
        }

        System.out.println(token);
        
        // Get userId from token
        Long userId = jwtUtil.getUserIdFromToken(token);

        if (workspaceRepository.existsByName(request.getName())) {
            throw new RuntimeException("Workspace with this name already exists");
        }

        Workspace workspace = new Workspace();
        workspace.setName(request.getName());
        workspace.setOwnerId(userId.toString());     
        
        workspace = workspaceRepository.save(workspace);
        
        return ResponseEntity.ok(new WorkspaceResponse(workspace.getId(), workspace.getName(), workspace.getOwnerId()));
    }
}