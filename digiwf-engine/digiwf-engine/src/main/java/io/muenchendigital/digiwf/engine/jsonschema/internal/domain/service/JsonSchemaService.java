/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.engine.jsonschema.internal.domain.service;

import io.muenchendigital.digiwf.engine.jsonschema.internal.domain.model.JsonSchema;
import io.muenchendigital.digiwf.engine.jsonschema.internal.domain.repository.JsonSchemaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service to interact with {@link }
 *
 * @author externer.dl.horn
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class JsonSchemaService {

    private final JsonSchemaRepository schemaRepository;

    public JsonSchema createJsonSchema(final JsonSchema jsonSchema) {
        return this.schemaRepository.save(jsonSchema);
    }

    public Optional<JsonSchema> getByKey(final String key) {
        return this.schemaRepository.findByKey(key);
    }
}
