package io.muenchendigital.digiwf.engine.configuration;

import io.muenchendigital.digiwf.engine.properties.DigiWFEngineProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@RequiredArgsConstructor
@EnableJpaRepositories(basePackages = "io.muenchendigital.digiwf.engine")
@EntityScan(basePackages = "io.muenchendigital.digiwf.engine")
@ComponentScan(basePackages = "io.muenchendigital.digiwf.engine")
@EnableConfigurationProperties(DigiWFEngineProperties.class)
public class DigiWFEngineAutoConfiguration {

    public final DigiWFEngineProperties digiWFEngineProperties;


}
