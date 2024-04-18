package de.muenchen.oss.digiwf.engine.incidents;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "digiwf.incident")
public class IncidentNotificationProperties {

    private String cockpitUrl;

    private String environment;

    private String fromAddress;

    private String toAddress;
}
