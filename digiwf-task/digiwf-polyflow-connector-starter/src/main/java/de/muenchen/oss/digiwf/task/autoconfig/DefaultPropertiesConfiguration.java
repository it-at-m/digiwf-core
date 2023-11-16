package de.muenchen.oss.digiwf.task.autoconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Order(Ordered.LOWEST_PRECEDENCE - 1)
@Configuration
@PropertySource(name = "defaultProperties", value = "application-polyflow-starter.yml", factory = YamlPropertySourceFactory.class)
public class DefaultPropertiesConfiguration {

}
