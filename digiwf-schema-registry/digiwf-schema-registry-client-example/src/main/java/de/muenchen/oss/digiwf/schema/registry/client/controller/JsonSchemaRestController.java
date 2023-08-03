/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.schema.registry.client.controller;

import de.muenchen.oss.digiwf.schema.registry.gen.api.JsonSchemaApi;
import de.muenchen.oss.digiwf.schema.registry.gen.model.JsonSchemaDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

/**
 * Rest API to handle schemas.
 *
 * @author externer.dl.horn
 */
@Validated
@RestController
@RequestMapping("/jsonschema")
@RequiredArgsConstructor
@Tag(name = "JsonSchema", description = "API to handle json schemas")
public class JsonSchemaRestController {

    private final JsonSchemaApi jsonSchemaApi;

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
            return ResponseEntity.ok(this.jsonSchemaApi.getJsonSchema(key));
        } catch (final NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        }
    }


}
