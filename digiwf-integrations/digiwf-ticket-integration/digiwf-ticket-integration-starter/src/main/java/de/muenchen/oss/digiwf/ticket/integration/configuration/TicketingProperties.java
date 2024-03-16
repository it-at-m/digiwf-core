package de.muenchen.oss.digiwf.ticket.integration.configuration;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "de.muenchen.oss.digiwf.ticketing")
public class TicketingProperties {

    @NotBlank
    private List<String> supportedFileExtensions;

}
