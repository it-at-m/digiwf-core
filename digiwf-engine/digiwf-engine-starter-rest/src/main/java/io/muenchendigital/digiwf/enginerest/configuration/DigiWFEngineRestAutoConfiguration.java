package io.muenchendigital.digiwf.enginerest.configuration;

import io.muenchendigital.digiwf.enginerest.properties.DigiWFEngineRestProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@RequiredArgsConstructor
@EnableJpaRepositories(basePackages = "io.muenchendigital.digiwf.enginerest")
@EntityScan(basePackages = "io.muenchendigital.digiwf.enginerest")
@ComponentScan(basePackages = "io.muenchendigital.digiwf.enginerest")
@EnableConfigurationProperties(DigiWFEngineRestProperties.class)
public class DigiWFEngineRestAutoConfiguration {

    public final DigiWFEngineRestProperties digiWFEngineProperties;


}
