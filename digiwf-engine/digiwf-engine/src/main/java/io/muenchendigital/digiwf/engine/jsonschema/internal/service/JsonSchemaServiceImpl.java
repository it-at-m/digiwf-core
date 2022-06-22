/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.engine.jsonschema.internal.service;

import io.muenchendigital.digiwf.engine.jsonschema.api.JsonSchema;
import io.muenchendigital.digiwf.engine.jsonschema.api.JsonSchemaService;
import io.muenchendigital.digiwf.engine.jsonschema.internal.model.JsonSchemaImpl;
import io.muenchendigital.digiwf.engine.jsonschema.internal.repository.JsonSchemaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service to interact with {@link JsonSchemaImpl }
 *
 * @author externer.dl.horn
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class JsonSchemaServiceImpl implements JsonSchemaService {

    private final JsonSchemaRepository schemaRepository;

    @Override
    public JsonSchema createJsonSchema(final JsonSchema jsonSchema) {
        final JsonSchemaImpl impl = (JsonSchemaImpl) jsonSchema;
        return this.schemaRepository.save(impl);
    }

    @Override
    public JsonSchema getByKey(final String key) {
        return this.schemaRepository.findByKey(key).orElseThrow();
    }
}
