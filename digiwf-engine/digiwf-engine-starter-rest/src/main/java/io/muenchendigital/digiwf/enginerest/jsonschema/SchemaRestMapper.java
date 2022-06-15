/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.enginerest.jsonschema;

import com.google.gson.Gson;
import io.muenchendigital.digiwf.engine.jsonschema.internal.domain.model.JsonSchema;
import io.muenchendigital.digiwf.json.factory.JsonSchemaFactory;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Map between {@link JsonSchemaDto} and {@link }
 *
 * @author externer.dl.horn
 */
@Mapper
public interface SchemaRestMapper {

    JsonSchema map2Model(JsonSchemaDto obj);

    default List<JsonSchemaDto> map2TO(final List<JsonSchema> list) {
        return list.stream().map(this::map2TO).collect(Collectors.toList());
    }

    JsonSchemaDto map2TO(JsonSchema obj);

    default String map(final Map<String, Object> jsonObject) {
        return new Gson().toJson(jsonObject);
    }

    default Map<String, Object> map(final String jsonString) {
        return JsonSchemaFactory.gson().fromJson(jsonString, JsonSchemaFactory.mapType());
    }

}
