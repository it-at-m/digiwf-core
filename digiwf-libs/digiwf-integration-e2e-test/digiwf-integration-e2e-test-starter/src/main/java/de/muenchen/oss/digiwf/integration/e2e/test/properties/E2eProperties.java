package de.muenchen.oss.digiwf.integration.e2e.test.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "de.muenchen.oss.digiwf.integration.e2e.test")
public class E2eProperties {

    private String integrationTopic;

}
