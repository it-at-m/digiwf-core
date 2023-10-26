package de.muenchen.oss.digiwf.shared.configuration.camunda;

import org.camunda.bpm.engine.rest.mapper.JacksonConfigurator;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class CamundaJacksonConfiguration {
  @PostConstruct
  public void configureDateFormat() {
    JacksonConfigurator.dateFormatString = "yyyy-MM-dd'T'HH:mm:ss.SSSX";
  }
}
