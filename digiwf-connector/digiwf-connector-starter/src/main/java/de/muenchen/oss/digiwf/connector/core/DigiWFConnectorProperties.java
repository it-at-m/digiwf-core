package de.muenchen.oss.digiwf.connector.core;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "de.muenchen.oss.digiwf.connector.core")
public class DigiWFConnectorProperties {
    private Map<String, String> integrations;
}
