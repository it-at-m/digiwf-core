package de.muenchen.oss.digiwf.connector.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;


@RequiredArgsConstructor
@ComponentScan(basePackages = "de.muenchen.oss.digiwf.connector")
@EnableConfigurationProperties(DigiWFEngineProperties.class)
public class DigiWFConnectorAutoConfiguration {

    public final DigiWFEngineProperties digiWFEngineProperties;

}
