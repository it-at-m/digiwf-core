/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf.alw.integration.configuration;

import de.muenchen.oss.digiwf.alw.integration.adapter.out.alw.AlwResponsibilityRestConfig;
import de.muenchen.oss.digiwf.alw.integration.adapter.out.integration.IntegrationOutAdapter;
import de.muenchen.oss.digiwf.alw.integration.application.port.out.IntegrationOutPort;
import de.muenchen.oss.digiwf.alw.integration.domain.model.AlwPingConfig;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.time.Duration;
import java.time.temporal.ChronoUnit;


@Configuration
@RequiredArgsConstructor
@ComponentScan(basePackages = {"de.muenchen.oss.digiwf.alw.integration"})
@EnableConfigurationProperties({
    AlwPersoneninfoProperties.class,
})
public class AlwAutoConfiguration {

  private final AlwPersoneninfoProperties alwPersoneninfoProperties;

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
    return restTemplateBuilder
        .basicAuthentication(alwPersoneninfoProperties.getUsername(), alwPersoneninfoProperties.getPassword(), Charset.defaultCharset())
        .setConnectTimeout(Duration.of(alwPersoneninfoProperties.getTimeout(), ChronoUnit.MILLIS))
        .build();
  }

  @Bean
  public AlwResponsibilityRestConfig alwConfig() {
    return new AlwResponsibilityRestConfig(
        alwPersoneninfoProperties.getBaseurl(),
        alwPersoneninfoProperties.getRestEndpoint()
    );
  }

  @Bean
  public AlwPingConfig alwPingConfig() {
    return new AlwPingConfig(
        alwPersoneninfoProperties.getFunctionalPing().isEnabled(),
        alwPersoneninfoProperties.getFunctionalPing().getAzrNumber()
    );
  }

  @Bean
  public IntegrationOutPort integration(ProcessApi processApi, ErrorApi errorApi) {
    return new IntegrationOutAdapter(processApi, errorApi);
  }

}
