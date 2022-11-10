/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.humantask.domain.model;

import io.muenchendigital.digiwf.legacy.form.domain.model.Form;
import lombok.*;

import java.util.Date;
import java.util.Map;

/**
 * Detail representation of a task.
 *
 * @author externer.dl.horn
 */
@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class HumanTaskDetail {

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

    /**
     * Actual variables related to the task.
     * Only includes variables that are contained in the associated form.
     */
    private final Map<String, Object> variables;

    /**
     * The corresponding form.
     */
    private Form form;

    /**
     * Status document download enabled.
     */
    private Boolean statusDocument;

    /**
     * json schema
     */
    private Map<String, Object> jsonSchema;

    public void setForm(final Form form) {
        this.form = form;
    }

    public void setJsonSchema(final Map<String, Object> jsonSchema) {
        this.jsonSchema = jsonSchema;
    }

    public void setStatusDocument(final Boolean setStatusDocument) {
        this.statusDocument = setStatusDocument;
    }

}
