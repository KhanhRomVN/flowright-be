package uk.gov.hmcts.reform.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.gov.hmcts.reform.demo.entity.Workspace;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
    boolean existsByName(String name);
}