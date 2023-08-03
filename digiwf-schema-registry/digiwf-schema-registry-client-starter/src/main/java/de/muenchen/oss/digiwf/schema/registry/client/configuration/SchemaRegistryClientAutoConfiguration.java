package de.muenchen.oss.digiwf.schema.registry.client.configuration;

import de.muenchen.oss.digiwf.schema.registry.client.properties.SchemaRegistryClientProperties;
import de.muenchen.oss.digiwf.schema.registry.gen.ApiClient;
import de.muenchen.oss.digiwf.schema.registry.gen.api.JsonSchemaApi;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@EnableConfigurationProperties(SchemaRegistryClientProperties.class)
public class SchemaRegistryClientAutoConfiguration {

    public final SchemaRegistryClientProperties schemaRegistryClientProperties;

    @Bean
    private ApiClient apiClient(final RestTemplate restTemplate) {
        final ApiClient apiClient = new ApiClient(restTemplate);
        apiClient.setBasePath(schemaRegistryClientProperties.getUrl());
        return apiClient;
    }

    @Bean
    public JsonSchemaApi jsonSchemaApi(final ApiClient apiClient) {
        return new JsonSchemaApi(apiClient);
    }

}
