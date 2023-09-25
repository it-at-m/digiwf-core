package de.muenchen.oss.digiwf.dms.integration.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "de.muenchen.oss.digiwf.dms")
public class DmsProperties {

    /**
     * Supported extensions.
     */
    @NotBlank
    private List<String> supportedExtensions;

}
