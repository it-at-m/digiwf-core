package de.muenchen.oss.digiwf.task.autoconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import static de.muenchen.oss.digiwf.task.autoconfig.DisableAxonKafkaPropertiesEnvironmentConfiguration.PROFILE_DISABLED;

@Order(Ordered.LOWEST_PRECEDENCE - 10)
@PropertySource(name = "disablePolyflowKafkaProperties", value = "application-disable-polyflow-kafka.yml", factory = YamlPropertySourceFactory.class)
@Profile(PROFILE_DISABLED)
@Configuration
public class DisableAxonKafkaPropertiesEnvironmentConfiguration {

  public static final String PROFILE_DISABLED = "disable-polyflow-kafka";
}
