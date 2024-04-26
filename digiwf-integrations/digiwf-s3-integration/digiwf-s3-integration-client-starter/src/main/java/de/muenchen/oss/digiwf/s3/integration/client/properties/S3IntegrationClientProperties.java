package de.muenchen.oss.digiwf.s3.integration.client.properties;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "de.muenchen.oss.digiwf.s3")
public class S3IntegrationClientProperties {

    private String documentStorageUrl;
    private boolean enableSecurity;
    /**
     * Supported file extensions.
     */
    private Map<String, String> supportedFileExtensions;

}
