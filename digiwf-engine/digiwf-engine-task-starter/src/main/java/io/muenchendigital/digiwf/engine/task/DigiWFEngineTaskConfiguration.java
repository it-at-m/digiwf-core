package io.muenchendigital.digiwf.engine.task;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@RequiredArgsConstructor
@EnableJpaRepositories(basePackages = "io.muenchendigital.digiwf.engine.task")
@EntityScan(basePackages = "io.muenchendigital.digiwf.engine.task")
@ComponentScan(basePackages = "io.muenchendigital.digiwf.engine.task")
@EnableConfigurationProperties(DigiWFEngineTaskProperties.class)
public class DigiWFEngineTaskConfiguration {

    public final DigiWFEngineTaskProperties digiWFEngineProperties;

}
