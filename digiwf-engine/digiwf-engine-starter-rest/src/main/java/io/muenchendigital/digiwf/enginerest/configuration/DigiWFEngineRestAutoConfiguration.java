package io.muenchendigital.digiwf.enginerest.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;


@RequiredArgsConstructor
@ComponentScan(basePackages = "io.muenchendigital.digiwf.enginerest")
@EnableConfigurationProperties(DigiWFEngineRestProperties.class)
public class DigiWFEngineRestAutoConfiguration {

    public final DigiWFEngineRestProperties digiWFEngineProperties;


}
