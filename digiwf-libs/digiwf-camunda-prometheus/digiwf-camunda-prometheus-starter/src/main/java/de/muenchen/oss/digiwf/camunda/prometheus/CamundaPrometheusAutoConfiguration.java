package de.muenchen.oss.digiwf.camunda.prometheus;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties(CamundaPrometheusProperties.class)
public class CamundaPrometheusAutoConfiguration {

}
