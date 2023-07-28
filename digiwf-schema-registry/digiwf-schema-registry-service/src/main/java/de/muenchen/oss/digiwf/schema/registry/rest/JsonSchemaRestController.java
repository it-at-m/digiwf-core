/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.schema.registry.rest;

import de.muenchen.oss.digiwf.schema.registry.api.JsonSchema;
import de.muenchen.oss.digiwf.schema.registry.api.JsonSchemaService;
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
@RequestMapping("/jsonschema")
@RequiredArgsConstructor
@Tag(name = "JsonSchema", description = "API to handle json schemas")
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
    @PreAuthorize("hasAuthority(T(de.muenchen.oss.digiwf.schema.registry.security.AuthoritiesEnum).BACKEND_DEPLOY_RESOURCE.name())")
    public ResponseEntity<JsonSchemaDto> createJsonSchema(@RequestBody @Valid final JsonSchemaDto dto) {
        final JsonSchema jsonSchema = this.schemaRestMapper.map2Model(dto);
        return ResponseEntity.ok(this.schemaRestMapper.map2TO(this.schemaService.createJsonSchema(jsonSchema)));
    }

    /**
     * Get a json schema by key
     *
     * @param key Key of the json schema
     * @return json schema
     */
    @GetMapping("/{key}")
    @Operation(description = "get json schema by key")
    public ResponseEntity<JsonSchemaDto> getJsonSchema(@PathVariable final String key) {
        try {
            final JsonSchema schema = this.schemaService.getByKey(key);
            return ResponseEntity.ok(this.schemaRestMapper.map2TO(schema));
        } catch (final NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        }
    }


}
