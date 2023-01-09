package io.muenchendigital.digiwf.jsonschema;

import io.muenchendigital.digiwf.testutils.Helper;
import lombok.extern.slf4j.Slf4j;
import org.everit.json.schema.ReadWriteContext;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.Validator;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

@Slf4j
public class BasicSchemaTest {

    @Test
    public void validateSchema() throws URISyntaxException, IOException {
        final JSONObject data = Helper.getJsonObject("/schema/basic/data.json");
        final JSONObject rawSchema = Helper.getJsonObject("/schema/basic/schema.json");
        final Schema schema = SchemaLoader.load(rawSchema);
        schema.validate(data);
    }

    @Test
    public void validateSimpleSchema() throws URISyntaxException, IOException {
        final JSONObject data = Helper.getJsonObject("/schema/basic/simpleData.json");
        final JSONObject rawSchema = Helper.getJsonObject("/schema/basic/simpleSchema.json");
        final Schema schema = SchemaLoader.load(rawSchema);
        schema.validate(data);
    }

    @Test
    public void failOnReadonlySimpleSchema() throws URISyntaxException, IOException {
        final JSONObject data = Helper.getJsonObject("/schema/basic/simpleData.json");
        final JSONObject rawSchema = Helper.getJsonObject("/schema/basic/simpleSchema.json");
        final Schema schema = SchemaLoader.builder().schemaJson(rawSchema)
                .draftV7Support()
                .build()
                .load()
                .build();

        final Validator validator = Validator.builder()
                .readWriteContext(ReadWriteContext.WRITE)
                .build();

        final ValidationException exception = assertThrows(ValidationException.class, () -> {
            validator.performValidation(schema, data);
        });

        assertThat(exception.getMessage()).isEqualTo("#/numberProp1: value is read-only");
    }
}
