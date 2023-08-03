package de.muenchen.oss.digiwf.json.serialization.configuration;

import de.muenchen.oss.digiwf.json.serialization.serializer.JsonSerializer;
import de.muenchen.oss.digiwf.json.serialization.JsonSerializationService;
import de.muenchen.oss.digiwf.json.serialization.serializer.JsonSerializerImpl;
import de.muenchen.oss.digiwf.json.validation.JsonSchemaValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class JsonSerializationAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public JsonSerializer jsonSchemaSerializer() {
        return new JsonSerializerImpl();
    }

    @Bean
    public JsonSerializationService jsonSchemaSerializationService(final JsonSerializer serializer) {
        return new JsonSerializationService(serializer);
    }

    @Bean
    public JsonSchemaValidator jsonSchemaValidator() {
        return new JsonSchemaValidator();
    }

}
