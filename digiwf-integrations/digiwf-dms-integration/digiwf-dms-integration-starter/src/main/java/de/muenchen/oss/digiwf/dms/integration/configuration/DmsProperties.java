package de.muenchen.oss.digiwf.dms.integration.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import jakarta.validation.constraints.NotBlank;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "de.muenchen.oss.digiwf.dms")
public class DmsProperties {

    /**
     * Supported extensions.
     */
    @NotBlank
    private Map<String,String> supportedExtensions;

}
