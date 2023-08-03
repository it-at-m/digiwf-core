/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.humantask.api.transport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Human transport object.
 *
 * @author externer.dl.horn
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HumanTaskTO {

    /**
     * Id of the task.
     */
    private String id;

    /**
     * Name of the task.
     */
    private String name;

    /**
     * Description of the task.
     */
    private String description;

    /**
     * Name of the corresponding process.
     */
    private String processName;

    /**
     * Assignee of the task.
     */
    private String assignee;

    /**
     * Formatted value of the assignee.
     */
    private String assigneeFormatted;

    /**
     * Follow up date of the task.
     */
    private String followUpDate;

    /**
     * Creation time of the task.
     */
    private Date creationTime;


}
