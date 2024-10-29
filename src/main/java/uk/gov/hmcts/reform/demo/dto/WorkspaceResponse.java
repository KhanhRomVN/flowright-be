package uk.gov.hmcts.reform.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkspaceResponse {
    private Long id;
    private String name;
    private String ownerId;
}