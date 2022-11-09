/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.humantask.api.transport;

import io.muenchendigital.digiwf.legacy.form.api.transport.FormTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Map;

;

/**
 * Detail transport object of a task.
 *
 * @author externer.dl.horn
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HumanTaskDetailTO {

    /**
     * Id of the task.
     */
    @NotNull
    private String id;

    /**
     * Name of the task.
     */
    @NotNull
    private String name;

    /**
     * Description of the task.
     */
    private String description;

    /**
     * Name of the corresponding process.
     */
    @NotNull
    private String processName;

    /**
     * Id of the corresponding process instance.
     */
    @NotNull
    private String processInstanceId;

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
    @NotNull
    private Date creationTime;

    /**
     * Actual variables related to the task.
     * Only includes variables that are contained in the associated form.
     */
    private Map<String, Object> variables;

    /**
     * The corresponding form.
     */
    private FormTO form;

    /**
     * json schema
     */
    private Map<String, Object> jsonSchema;

    /**
     * Status document download enabled.
     */
    private Boolean statusDocument;

}
