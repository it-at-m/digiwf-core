package de.muenchen.oss.digiwf.task.service.infra.engine;

import feign.Logger;
import org.camunda.community.rest.EnableCamundaRestClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures REST client.
 */
@Configuration
@EnableCamundaRestClient
public class RemoteCamundaClientConfiguration {

  @Bean
  public Logger.Level feignLoggerLevel() {
    return Logger.Level.FULL;
  }

}
