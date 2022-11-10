/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.humantask.domain.model;

import lombok.*;

/**
 * Additional task info object for performance optimization.
 *
 * @author externer.dl.horn
 */
@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class TaskInfo {

    /**
     * Id of the task.
     */
    private final String id;

    /**
     * Description of the task.
     */
    private final String description;

    /**
     * Name of the corresponding service definition.
     */
    private final String definitionName;

    /**
     * Assignee of the task.
     */
    private String assignee;

    /**
     * Instance Id
     */
    private String instanceId;


    public void updateAssignee(final String assignee) {
        this.assignee = assignee;
    }

}
