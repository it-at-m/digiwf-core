package de.muenchen.oss.digiwf.cockpit.envprocessor;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Order(Ordered.LOWEST_PRECEDENCE - 1)
@Configuration
@PropertySource(name = "webAppProperties", value = "application-webapps.yml", factory = YamlPropertySourceFactory.class)
public class CamundaWebAppsConfiguration {

}
