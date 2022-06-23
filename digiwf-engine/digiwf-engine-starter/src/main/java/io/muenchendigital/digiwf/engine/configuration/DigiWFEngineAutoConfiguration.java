package io.muenchendigital.digiwf.engine.configuration;

import io.muenchendigital.digiwf.engine.jsonschema.api.JsonSchemaService;
import io.muenchendigital.digiwf.engine.jsonschema.internal.deployment.JsonSchemaAutodeployment;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@RequiredArgsConstructor
@EnableJpaRepositories(basePackages = "io.muenchendigital.digiwf.engine")
@EntityScan(basePackages = "io.muenchendigital.digiwf.engine")
@ComponentScan(basePackages = "io.muenchendigital.digiwf.engine")
@EnableConfigurationProperties(DigiWFEngineProperties.class)
public class DigiWFEngineAutoConfiguration {

    public final DigiWFEngineProperties digiWFEngineProperties;

    @Bean
    @ConditionalOnProperty("io.muenchendigital.digiwf.engine.jsonschema.autodeploy")
    public JsonSchemaAutodeployment jsonSchemaAutodeployment(final ResourceLoader resourceLoader, final JsonSchemaService jsonSchemaService) {
        return new JsonSchemaAutodeployment(resourceLoader, jsonSchemaService);
    }

}
