/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.service.definition.api.transport;

import io.muenchendigital.digiwf.legacy.form.api.transport.FormTO;
import io.muenchendigital.digiwf.service.definition.domain.model.ServiceDefinitionDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * Transport object of the {@link ServiceDefinitionDetail} object
 *
 * @author externer.dl.horn
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDefinitionDetailTO {

    /**
     * Start form of the service definition.
     */
    private FormTO startForm;

    /**
     * json schema
     */
    private Map<String, Object> jsonSchema;

    /**
     * Key of the service definition.
     * Used to start the service.
     */
    @NotBlank
    private String key;

    /**
     * Name
     */
    @NotBlank
    private String name;

    /**
     * Description
     */
    private String description;

    /**
     * version tag
     */
    private String versionTag;

}
