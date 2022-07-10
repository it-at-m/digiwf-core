package io.muenchendigital.digiwf.connectorrest.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;


@RequiredArgsConstructor
@ComponentScan(basePackages = "io.muenchendigital.digiwf.connectorrest")
@EnableConfigurationProperties(DigiWFEngineRestProperties.class)
public class DigiWFEngineRestAutoConfiguration {

    public final DigiWFEngineRestProperties digiWFEngineProperties;


}
