package de.muenchen.oss.digiwf.connector.core;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@RequiredArgsConstructor
@ComponentScan(basePackages = "de.muenchen.oss.digiwf.connector.core")
@EnableConfigurationProperties(DigiWFConnectorProperties.class)
public class DigiWFConnectorAutoConfiguration {

}
