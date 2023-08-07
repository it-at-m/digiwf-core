/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.process.definition.domain.model;

import de.muenchen.oss.digiwf.legacy.form.domain.model.Form;
import lombok.*;

import java.util.Map;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ServiceDefinitionDetail {

    /**
     * Id of the process definition.
     */
    private final String id;

    /**
     * Start form of the process definition.
     */
    private Form startForm;

    /**
     * json schema
     */
    private Map<String, Object> jsonSchema;

    /**
     * Key of the process definition.
     */
    private final String key;

    /**
     * Name of the process definition.
     */
    private final String name;

    /**
     * Description provides further information about the process definition.
     */
    private final String description;

    /**
     * Versiontag of the process definition.
     */
    private final String versionTag;

    public void setForm(final Form form) {
        this.startForm = form;
    }

    public void setJsonSchema(final Map<String, Object> jsonSchema) {
        this.jsonSchema = jsonSchema;
    }

}
