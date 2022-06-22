package io.muenchendigital.digiwf.enginestreaming.configuration;

import io.muenchendigital.digiwf.enginestreaming.properties.DigiWFEngineStreamingProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;


@RequiredArgsConstructor
@ComponentScan(basePackages = "io.muenchendigital.digiwf.enginerest")
@EnableConfigurationProperties(DigiWFEngineStreamingProperties.class)
public class DigiWFEngineStreamingAutoConfiguration {

    public final DigiWFEngineStreamingProperties digiWFEngineStreamingProperties;


}
