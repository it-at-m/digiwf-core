/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.schema.registry.internal.impl.service;

import de.muenchen.oss.digiwf.schema.registry.api.JsonSchema;
import de.muenchen.oss.digiwf.schema.registry.api.JsonSchemaService;
import de.muenchen.oss.digiwf.schema.registry.internal.impl.mapper.JsonSchemaMapper;
import de.muenchen.oss.digiwf.schema.registry.internal.impl.model.JsonSchemaImpl;
import de.muenchen.oss.digiwf.schema.registry.internal.impl.repository.JsonSchemaRepository;
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
    private final JsonSchemaMapper jsonSchemaMapper;

    @Override
    public JsonSchema createJsonSchema(final JsonSchema jsonSchema) {
        final JsonSchemaImpl impl = this.jsonSchemaMapper.map(jsonSchema);
        return this.schemaRepository.save(impl);
    }

    @Override
    public JsonSchema getByKey(final String key) {
        return this.schemaRepository.findByKey(key).orElseThrow();
    }
}
