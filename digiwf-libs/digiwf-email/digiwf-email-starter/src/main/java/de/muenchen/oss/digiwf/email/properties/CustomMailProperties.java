package de.muenchen.oss.digiwf.email.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "io.muenchendigital.digiwf.mail")
public class CustomMailProperties {

    /**
     * Sender mail address.
     */
    private String fromAddress;

}
