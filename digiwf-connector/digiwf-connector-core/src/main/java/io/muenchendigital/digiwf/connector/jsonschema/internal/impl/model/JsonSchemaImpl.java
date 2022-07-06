/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.connector.jsonschema.internal.impl.model;

import io.muenchendigital.digiwf.connector.jsonschema.api.JsonSchema;
import io.muenchendigital.digiwf.json.factory.JsonSchemaFactory;
import lombok.*;

import javax.persistence.*;
import java.util.Map;

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
@Entity(name = "JsonSchema")
@Table(name = "DWF_JSON_SCHEMA", indexes = {@Index(name = "IDX_DWF_SCHEMAKEY", columnList = "key_")})
public class JsonSchemaImpl implements JsonSchema {

    @Id
    @Column(name = "key_")
    private String key;

    @Column(name = "schema_", columnDefinition = "CLOB")
    private String schema;

    public Map<String, Object> getSchemaMap() {
        return JsonSchemaFactory.gson().fromJson(this.schema, JsonSchemaFactory.mapType());
    }
}
