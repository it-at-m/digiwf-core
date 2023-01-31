/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.process.definition.api.transport;

import io.muenchendigital.digiwf.process.definition.domain.model.ServiceDefinition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Transport object of the {@link ServiceDefinition}
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDefinitionTO {

    /**
     * Key of the process definition.
     */
    private String key;

    /**
     * Name of the process definition.
     */
    private String name;

    /**
     * Description of the process definition.
     */
    private String description;

    /**
     * Versiontag of the process definition.
     */
    private String versionTag;

}
