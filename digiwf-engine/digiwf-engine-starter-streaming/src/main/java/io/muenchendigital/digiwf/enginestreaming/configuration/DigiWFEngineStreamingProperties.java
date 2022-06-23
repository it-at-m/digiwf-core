package io.muenchendigital.digiwf.enginestreaming.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "io.muenchendigital.digiwf.enginestreaming")
public class DigiWFEngineStreamingProperties {


}
