package de.muenchen.oss.digiwf.ticket.integration.configuration;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@ConfigurationProperties(prefix = "de.muenchen.oss.digiwf.ticketing")
public class TicketingProperties {

    /**
     * Supported extensions.
     */
    @NotBlank
    private Map<String, String> supportedFileExtensions;

}
