package io.muenchendigital.digiwf.humantask.infrastructure.entity.camunda;

import io.muenchendigital.digiwf.humantask.infrastructure.entity.TaskInfoEntity;
import lombok.*;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ActRuTask")
@Immutable
@Table(name = "ACT_RU_TASK")
public class ActRuTaskEntity {

    @Id
    @Column(name = "id_", unique = true, nullable = false, length = 64)
    private String id;

    @Column(name = "assignee_", nullable = true, length = 255)
    private String assignee;

    @Column(name = "name_", nullable = false, length = 255)
    private String name;

    @Column(name = "create_time_", nullable = false)
    private Date createdAt;
    @Column(name = "follow_up_date_")
    private Date followUpDate;

    @OneToMany(mappedBy = "actRuTaskEntity")
    private List<ActRuIdentityLinkEntity> actRuIdentities;

    @OneToOne
    @JoinColumn(name = "id_", referencedColumnName = "id_")
    private TaskInfoEntity taskInfoEntity;

    public String getId() {
        return id;
    }

    public String getAssignee() {
        return assignee;
    }
}
