package de.muenchen.oss.digiwf.schema.registry.internal.deployment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import de.muenchen.oss.digiwf.schema.registry.api.JsonSchema;
import de.muenchen.oss.digiwf.schema.registry.api.JsonSchemaService;
import de.muenchen.oss.digiwf.schema.registry.internal.impl.model.JsonSchemaImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.util.FileCopyUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Auto deployment of json schemas.
 *
 * @author externer.dl.horn
 */
@Slf4j
@ConditionalOnProperty("io.muenchendigital.schema.registry.autodeployment.enabled")
@RequiredArgsConstructor
public class JsonSchemaAutodeployment {

    private final ResourceLoader resourceLoader;
    private final JsonSchemaService jsonSchemaService;

    @PostConstruct
    public void autoDeploy() throws IOException {
        final Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(this.resourceLoader).getResources("classpath:**/*.schema.json");
        final List<JsonSchema> schemas = new ArrayList<>();
        for (final Resource resource : resources) {
            try {
                schemas.add(this.map(resource));
            } catch (final Exception error) {
                log.error("The schema could no be loaded: {}", resource.getFilename(), error);
            }
        }

        for (final JsonSchema schema : schemas) {
            try {
                this.jsonSchemaService.createJsonSchema(schema);
            } catch (final Exception error) {
                log.error("The schema could no be loaded: {}", schema.getKey(), error);
            }
        }
    }

    private JsonSchema map(final Resource resource) throws IOException {
        val json = this.asString(resource);
        final Type type = new TypeToken<Map<String, Object>>() {
        }.getType();
        final Map<String, Object> schemaMap = new Gson().fromJson(json, type);
        return new JsonSchemaImpl(schemaMap.get("key").toString(), new Gson().toJson(schemaMap.get("schema"))
        );
    }

    private String asString(final Resource resource) throws IOException {
        try (final Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        }
    }
}
