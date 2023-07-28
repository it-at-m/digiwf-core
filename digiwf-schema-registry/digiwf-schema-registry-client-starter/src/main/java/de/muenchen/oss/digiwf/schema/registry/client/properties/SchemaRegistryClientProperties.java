package de.muenchen.oss.digiwf.schema.registry.client.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "io.muenchendigital.digiwf.schema.registry.client")
public class SchemaRegistryClientProperties {

    private String url;

}
