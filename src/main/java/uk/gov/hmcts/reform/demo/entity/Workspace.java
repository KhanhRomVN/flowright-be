package uk.gov.hmcts.reform.demo.entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "workspaces")
@Data
public class Workspace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;
}