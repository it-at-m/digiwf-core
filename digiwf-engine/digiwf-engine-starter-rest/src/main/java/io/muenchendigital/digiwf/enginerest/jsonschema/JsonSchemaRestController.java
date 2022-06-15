/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.enginerest.jsonschema;

import io.muenchendigital.digiwf.engine.jsonschema.internal.domain.model.JsonSchema;
import io.muenchendigital.digiwf.engine.jsonschema.internal.domain.service.JsonSchemaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Rest API to handle schemas.
 *
 * @author externer.dl.horn
 */
@Validated
@Transactional
@RestController
@RequestMapping("/rest/jsonschema")
@RequiredArgsConstructor
@Tag(name = "SchemaRestController", description = "API to handle json schemas")
public class JsonSchemaRestController {

    private final JsonSchemaService schemaService;
    private final SchemaRestMapper schemaApiMapper;

    /**
     * Create a new json schema.
     *
     * @param dto Json schema that is created
     * @return json schema
     */
    @PostMapping
    @Operation(description = "create a new json schema")
    @PreAuthorize("hasAuthority(T(io.muenchendigital.digiwf.engine.security.api.AuthoritiesEnum).BACKEND_DEPLOY_RESOURCE.name())")
    public ResponseEntity<JsonSchemaDto> createJsonSchema(@RequestBody @Valid final JsonSchemaDto dto) {
        final JsonSchema jsonSchema = this.schemaApiMapper.map2Model(dto);
        return ResponseEntity.ok(this.schemaApiMapper.map2TO(this.schemaService.createJsonSchema(jsonSchema)));
    }

    /**
     * Get a json schema by key
     *
     * @param key Key of the json schema
     * @return json schema
     */
    @GetMapping("/{key}")
    @Operation(description = "get json schema by key")
    @PreAuthorize("hasAuthority(T(io.muenchendigital.digiwf.engine.security.api.AuthoritiesEnum).BACKEND_DEPLOY_RESOURCE.name())")
    public ResponseEntity<JsonSchemaDto> getJsonSchema(@PathVariable final String key) {
        final Optional<JsonSchema> schema = this.schemaService.getByKey(key);
        if (schema.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(this.schemaApiMapper.map2TO(schema.get()));
    }


}
