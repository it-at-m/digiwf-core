package de.muenchen.oss.digiwf.address.integration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "de.muenchen.oss.digiwf.address.service")
public class AddressServiceIntegrationProperties {

    @NotBlank
    private String url;

}
