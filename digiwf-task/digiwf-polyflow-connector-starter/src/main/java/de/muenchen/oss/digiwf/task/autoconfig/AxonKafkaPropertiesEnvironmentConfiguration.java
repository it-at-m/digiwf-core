package de.muenchen.oss.digiwf.task.autoconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Order(Ordered.LOWEST_PRECEDENCE - 10)
@PropertySource(name = "polyflowKafkaProperties", value = "application-polyflow-kafka.yml", factory = YamlPropertySourceFactory.class)
@Profile(AxonKafkaPropertiesEnvironmentConfiguration.PROFILE)
@Configuration
public class AxonKafkaPropertiesEnvironmentConfiguration {
  public static final String PROFILE = "polyflow-kafka";
}
