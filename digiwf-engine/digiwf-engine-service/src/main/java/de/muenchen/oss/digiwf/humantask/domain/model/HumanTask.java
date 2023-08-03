/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.humantask.domain.model;

import lombok.*;

import java.util.Date;

/**
 * Object represents a task in digitalwf.
 *
 * @author externer.dl.horn
 */
@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class HumanTask {

    /**
     * Id of the task.
     */
    private final String id;

    /**
     * Name of the task.
     */
    private final String name;

    /**
     * Description of the task.
     */
    private final String description;

    /**
     * Name of the corresponding process.
     */
    private final String processName;

    /**
     * Id of the corresponding process instance.
     */
    private String processInstanceId;

    /**
     * Assignee of the task.
     */
    private final String assignee;

    /**
     * Formatted value of the assignee.
     */
    private final String assigneeFormatted;

    /**
     * Follow up date of the task.
     */
    private final String followUpDate;

    /**
     * Creation time of the task.
     */
    private final Date creationTime;

}
