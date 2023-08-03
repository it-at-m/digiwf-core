package de.muenchen.oss.digiwf.connector.rest.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;


@RequiredArgsConstructor
@ComponentScan(basePackages = "de.muenchen.oss.digiwf.connectorrest")
@EnableConfigurationProperties(DigiWFEngineRestProperties.class)
public class DigiWFEngineRestAutoConfiguration {

    public final DigiWFEngineRestProperties digiWFEngineProperties;


}
