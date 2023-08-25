package io.muenchendigital.digiwf.okverkehr.integration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "io.muenchendigital.digiwf.okverkehr")
public class OkVerkehrIntegrationProperties {

    @NotBlank
    private String url;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
