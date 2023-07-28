/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.schema.registry.rest;

import com.google.gson.Gson;
import de.muenchen.oss.digiwf.json.factory.JsonSchemaFactory;
import io.muenchendigital.digiwf.schema.registry.api.JsonSchema;
import io.muenchendigital.digiwf.schema.registry.internal.impl.model.JsonSchemaImpl;
import org.mapstruct.Mapper;

import java.util.Map;

@Mapper
public interface SchemaRestMapper {

    JsonSchemaDto map2TO(JsonSchema obj);

    JsonSchemaImpl map2Model(JsonSchemaDto obj);

    default String map(final Map<String, Object> jsonObject) {
        return new Gson().toJson(jsonObject);
    }

    default Map<String, Object> map(final String jsonString) {
        return JsonSchemaFactory.gson().fromJson(jsonString, JsonSchemaFactory.mapType());
    }

}
