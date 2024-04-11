package de.muenchen.oss.digiwf.engine.incidents;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "digiwf.incident")
public class IncidentNotificationProperties {

    private String cockpiturl;

    private String environment;

    private String fromaddress;

    private String toaddress;
}
