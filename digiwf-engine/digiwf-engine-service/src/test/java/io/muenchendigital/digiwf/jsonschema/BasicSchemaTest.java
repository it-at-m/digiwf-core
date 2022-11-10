package io.muenchendigital.digiwf.jsonschema;

import lombok.extern.slf4j.Slf4j;
import org.everit.json.schema.ReadWriteContext;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.Validator;
import org.everit.json.schema.loader.SchemaLoader;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

@Slf4j
public class BasicSchemaTest {

    @Test
    public void validateSchema() throws URISyntaxException, IOException {
        final JSONObject data = this.getJsonObject("/schema/basic/data.json");
        final JSONObject rawSchema = this.getJsonObject("/schema/basic/schema.json");
        final Schema schema = SchemaLoader.load(rawSchema);
        schema.validate(data);
    }

    @Test
    public void validateSimpleSchema() throws URISyntaxException, IOException {
        final JSONObject data = this.getJsonObject("/schema/basic/simpleData.json");
        final JSONObject rawSchema = this.getJsonObject("/schema/basic/simpleSchema.json");
        final Schema schema = SchemaLoader.load(rawSchema);
        schema.validate(data);
    }

    @Test
    public void failOnReadonlySimpleSchema() throws URISyntaxException, IOException {
        final JSONObject data = this.getJsonObject("/schema/basic/simpleData.json");
        final JSONObject rawSchema = this.getJsonObject("/schema/basic/simpleSchema.json");
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

    //------------------------------------ Helper Methods ------------------------------------//

    @NotNull
    private JSONObject getJsonObject(final String path) throws IOException, URISyntaxException {
        final String schemaString = new String(Files.readAllBytes(Paths.get(this.getClass().getResource(path).toURI())));
        return new JSONObject(new JSONTokener(schemaString));
    }

}
