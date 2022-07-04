/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.connectorrest.jsonschema;

import io.muenchendigital.digiwf.connector.jsonschema.api.JsonSchema;
import io.muenchendigital.digiwf.connector.jsonschema.api.JsonSchemaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.NoSuchElementException;

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
@Tag(name = "JsonSchemaApi", description = "API to handle json schemas")
public class JsonSchemaRestController {

    private final JsonSchemaService schemaService;
    private final SchemaRestMapper schemaRestMapper;

    /**
     * Create a new json schema.
     *
     * @param dto Json schema that is created
     * @return json schema
     */
    @PostMapping
    @Operation(description = "create a new json schema")
    @PreAuthorize("hasAuthority(T(io.muenchendigital.digiwf.connector.security.api.AuthoritiesEnum).BACKEND_DEPLOY_RESOURCE.name())")
    public ResponseEntity<JsonSchemaDto> createJsonSchema(@RequestBody @Valid final JsonSchemaDto dto) {
        return ResponseEntity.ok(this.schemaRestMapper.map2TO(this.schemaService.createJsonSchema(dto)));
    }

    /**
     * Get a json schema by key
     *
     * @param key Key of the json schema
     * @return json schema
     */
    @GetMapping("/{key}")
    @Operation(description = "get json schema by key")
    @PreAuthorize("hasAuthority(T(io.muenchendigital.digiwf.connector.security.api.AuthoritiesEnum).BACKEND_DEPLOY_RESOURCE.name())")
    public ResponseEntity<JsonSchemaDto> getJsonSchema(@PathVariable final String key) {
        try {
            final JsonSchema schema = this.schemaService.getByKey(key);
            return ResponseEntity.ok(this.schemaRestMapper.map2TO(schema));
        } catch (final NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        }
    }


}
