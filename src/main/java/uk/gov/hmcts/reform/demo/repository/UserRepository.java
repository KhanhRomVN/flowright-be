package uk.gov.hmcts.reform.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.gov.hmcts.reform.demo.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}