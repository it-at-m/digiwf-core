package de.muenchen.oss.digiwf.integration.e2e.test.autoconfiguration;

import de.muenchen.oss.digiwf.integration.e2e.test.factory.YamlPropertySourceFactory;
import de.muenchen.oss.digiwf.integration.e2e.test.properties.E2eProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Profile("itest")
@ComponentScan(basePackages = "de.muenchen.oss.digiwf.integration.e2e.test")
@PropertySource(value = "classpath:integration-e2e-test-application.yaml", factory = YamlPropertySourceFactory.class)
@EnableConfigurationProperties(E2eProperties.class)
public class E2eAutoConfiguration {

}
