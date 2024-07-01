package de.muenchen.oss.digiwf.openai.integration.properties;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@Component
@ConfigurationProperties(prefix = "digiwf.openai.azure")
@Profile("azure")
public class AzureIntegrationProperties {

    @NotBlank
    private String apiVersion;

    @NotBlank
    private String deploymentName;

    @NotBlank
    private String resource;

}
