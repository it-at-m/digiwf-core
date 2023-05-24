package io.muenchendigital.digiwf.shared.configuration;

import org.camunda.bpm.engine.rest.mapper.JacksonConfigurator;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamundaJacksonConfiguration {

    CamundaJacksonConfiguration() {
        JacksonConfigurator.dateFormatString = "yyyy-MM-dd'T'HH:mm:ss.SSSX";
    }

}
