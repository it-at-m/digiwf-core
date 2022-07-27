/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.json.validation;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.everit.json.schema.regexp.RE2JRegexpFactory;
import org.json.JSONObject;

import java.util.Map;

/**
 * Json Schema Validator
 */
public class JsonSchemaValidator {

    /**
     * Validates data against a json schema
     *
     * @param schema schema that is used for validation
     * @param data   data that is validated
     */
    public void validate(final Map<String, Object> schema, final Map<String, Object> data) {
        this.validate(schema, new JSONObject(data));
    }

    /**
     * Validates data against a json schema
     *
     * @param schema schema that is used for validation
     * @param data   data that is validated
     */
    public void validate(final String schema, final Map<String, Object> data) {
        final Schema schemaObj = this.createSchema(new JSONObject(schema));
        schemaObj.validate(new JSONObject(data));
    }


    //------------------------------------- helper methods -------------------------------------//

    private void validate(final Map<String, Object> schemaObject, final JSONObject data) {
        final Schema schema = this.createSchema(new JSONObject(schemaObject));
        schema.validate(data);
    }

    private Schema createSchema(final JSONObject schemaObject) {
        return SchemaLoader.builder().schemaJson(schemaObject)
                .draftV7Support()
                .regexpFactory(new RE2JRegexpFactory())
                .build()
                .load()
                .build();
    }
}
