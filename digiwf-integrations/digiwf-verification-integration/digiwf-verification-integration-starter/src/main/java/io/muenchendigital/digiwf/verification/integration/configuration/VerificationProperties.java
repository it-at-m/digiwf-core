package io.muenchendigital.digiwf.verification.integration.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotBlank;

@Data
@ConfigurationProperties(prefix = "io.muenchendigital.digiwf.verification.integration")
public class VerificationProperties {

    /**
     * Url of the verification service.
     */
    @NotBlank
    private String baseAddress;

}
