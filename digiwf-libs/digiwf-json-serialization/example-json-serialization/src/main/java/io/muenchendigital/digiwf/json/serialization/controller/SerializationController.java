package io.muenchendigital.digiwf.json.serialization.controller;

import io.muenchendigital.digiwf.json.serialization.JsonSerializationService;
import io.muenchendigital.digiwf.json.serialization.dto.DataDto;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Demo Controller which serializes and deserializes json schema data
 */
@RequiredArgsConstructor
@RestController
public class SerializationController {

    // inject JsonSchemaSerializationService
    private final JsonSerializationService jsonSchemaSerializationService;

    @PostMapping(path = "/serialize")
    public Map<String, Object> serialize(@RequestBody final DataDto body) throws IOException, URISyntaxException {
        final String rawSchema = this.getSchemaString(body.getSchema());
        final JSONObject filteredData = this.jsonSchemaSerializationService.filter(rawSchema, body.getData(), true);
        return this.jsonSchemaSerializationService.merge(filteredData, new JSONObject(body.getPreviousData()));
    }

    @PostMapping(path = "/deserialize")
    public Map<String, Object> deserialize(@RequestBody final DataDto body) throws IOException, URISyntaxException {
        final String rawSchema = this.getSchemaString(body.getSchema());
        return this.jsonSchemaSerializationService.deserializeData(rawSchema, body.getData());
    }

    private String getSchemaString(final String path) throws IOException, URISyntaxException {
        return new String(Files.readAllBytes(Paths.get(this.getClass().getResource(path).toURI())));
    }
}
