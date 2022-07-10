package io.muenchendigital.digiwf.connector.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@RequiredArgsConstructor
@EnableJpaRepositories(basePackages = "io.muenchendigital.digiwf.connector")
@EntityScan(basePackages = "io.muenchendigital.digiwf.connector")
@ComponentScan(basePackages = "io.muenchendigital.digiwf.connector")
@EnableConfigurationProperties(DigiWFEngineProperties.class)
public class DigiWFConnectorAutoConfiguration {

    public final DigiWFEngineProperties digiWFEngineProperties;

}
