package de.muenchen.oss.digiwf.task.service.infra.metrics;

import io.prometheus.client.CollectorRegistry;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CollectorRegistryMockingConfiguration {

  @Bean
  public CollectorRegistry mockRegistry() {
   return Mockito.mock(CollectorRegistry.class);
  }
}
