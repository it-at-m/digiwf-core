package de.muenchen.oss.digiwf.openai.integration.properties;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "de.muenchen.oss.digiwf.openai.service")
public class OpenAiIntegrationProperties {

    @NotBlank
    private String baseUrl;

    @NotBlank
    private String deploymentName;

    @NotBlank
    private String apiKey;

    @NotBlank
    private String model;

    @NotBlank
    private String apiVersion;

    private Integer maxTokens = 1000;

    private Double temperature = 0.7;

    private Boolean logging = false;

}
