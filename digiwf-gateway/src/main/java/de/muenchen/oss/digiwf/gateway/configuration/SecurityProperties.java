package de.muenchen.oss.digiwf.gateway.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Getter
@Setter
@ConfigurationProperties("digiwf.security")
public class SecurityProperties {
    /**
     * List of url patterns excluded from csrf protection.
     */
    private List<String> csrfWhitelisted = List.of();
}
