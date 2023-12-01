/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.humantask.infrastructure.entity;

import lombok.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entity representation of a form.
 *
 * @author externer.dl.horn
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TaskInfo")
@Table(name = "DWF_TASK_INFO")
public class TaskInfoEntity {

    @Id
    @Column(name = "id_", unique = true, nullable = false, length = 36)
    private String id;

    @Column(name = "description_")
    private String description;

    @Column(name = "definitionname_", nullable = false)
    private String definitionName;

    @Column(name = "instanceid_", nullable = false)
    private String instanceId;

    @Column(name = "assignee_")
    private String assignee;

}
