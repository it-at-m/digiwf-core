package io.muenchendigital.digiwf.humantask.infrastructure.entity.camunda;

import lombok.*;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ActRuIdentityLink")
@Immutable
@Table(name = "ACT_RU_IDENTITYLINK")
public class ActRuIdentityLinkEntity {

    @Id
    @Column(name = "id_", unique = true, nullable = false, length = 64)
    private String id;

    @ManyToOne
    @JoinColumn(name="task_id_", nullable=false)
    @ToString.Exclude
    private ActRuTaskEntity actRuTaskEntity;
    @Column(name = "user_id_")
    private String userId;
    @Column(name = "group_id_")
    private String groupId;
    @Column(name = "type_")
    private String type;

}
