package io.muenchendigital.digiwf.connector.jsonschema.internal;

import io.muenchendigital.digiwf.connector.BaseSpringEntityTest;
import io.muenchendigital.digiwf.connector.jsonschema.api.JsonSchema;
import io.muenchendigital.digiwf.connector.jsonschema.api.JsonSchemaService;
import io.muenchendigital.digiwf.connector.jsonschema.internal.impl.mapper.JsonSchemaMapper;
import io.muenchendigital.digiwf.connector.jsonschema.internal.impl.mapper.JsonSchemaMapperImpl;
import io.muenchendigital.digiwf.connector.jsonschema.internal.impl.model.JsonSchemaImpl;
import io.muenchendigital.digiwf.connector.jsonschema.internal.impl.repository.JsonSchemaRepository;
import io.muenchendigital.digiwf.connector.jsonschema.internal.impl.service.JsonSchemaServiceImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Streaming Service Test")
@Import(JsonSchemaMapperImpl.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JsonSchemaServiceTest extends BaseSpringEntityTest {

    @Autowired
    private JsonSchemaRepository jsonSchemaRepository;

    @Autowired
    private JsonSchemaMapper jsonSchemaMapper;

    private JsonSchemaService jsonSchemaService;

    @BeforeEach
    private void initTests() {
        this.jsonSchemaService = new JsonSchemaServiceImpl(this.jsonSchemaRepository, this.jsonSchemaMapper);
    }

    @Order(1)
    @Test
    @DisplayName("shouldCreateJsonSchema")
    @Rollback(false)
    public void shouldCreateJsonSchema() {

        final JsonSchema jsonSchema = JsonSchemaImpl.builder()
                .key("myKey")
                .schema("{'key':'value'}")
                .build();

        this.jsonSchemaService.createJsonSchema(jsonSchema);

        final JsonSchema created = this.jsonSchemaService.getByKey("myKey");

        assertThat(jsonSchema).usingRecursiveComparison().isEqualTo(created);
    }

    @Order(1)
    @Test
    @DisplayName("shouldCreateJsonSchema")
    @Rollback(false)
    public void shouldCreatefromAnotherImpl() {

        final JsonSchema jsonSchema = JsonSchemaTestImpl.builder()
                .key("myKey")
                .schema("{'key':'value'}")
                .build();

        this.jsonSchemaService.createJsonSchema(jsonSchema);

        final JsonSchema created = this.jsonSchemaService.getByKey("myKey");

        assertThat(jsonSchema).usingRecursiveComparison().isEqualTo(created);
    }

}
