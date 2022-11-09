/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.jsonschema.api.transport;

import io.muenchendigital.digiwf.jsonschema.domain.model.JsonSchema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Transport object of the {@link JsonSchema}
 *
 * @author externer.dl.horn
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JsonSchemaTO {

    /**
     * Key of the json schema.
     */
    @NotBlank
    private String key;

    /**
     * Json schema string
     */
    @NotNull
    private Map<String, Object> schema;

}
