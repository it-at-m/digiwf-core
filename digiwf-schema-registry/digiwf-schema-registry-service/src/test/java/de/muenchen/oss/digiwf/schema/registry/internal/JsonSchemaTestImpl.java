/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.schema.registry.internal;

import de.muenchen.oss.digiwf.schema.registry.api.JsonSchema;
import lombok.*;

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
public class JsonSchemaTestImpl implements JsonSchema {

    private String key;

    private String schema;

}
