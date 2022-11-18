package io.muenchendigital.digiwf.humantask.infrastructure.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ActRuTask")
@Table(name = "ACT_RU_TASK")
public class ActRuTaskEntity {

    @Id
    @Column(name = "id_", unique = true, nullable = false, length = 64)
    private String id;

    @Column(name = "assignee_", nullable = false, length = 255)
    private String assignee;

    @Column(name = "name_", nullable = false, length = 255)
    private String name;

    @Column(name = "create_time_", nullable = false)
    private Date createdAt;
    @Column(name = "follow_up_data_")
    private Date followUpDate;
}
