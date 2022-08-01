package io.muenchendigital.digiwf.spring.cloudstream.utils.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "io.muenchendigital.digiwf.streaming")
public class StreamingProperties {

    /**
     * A map of typeMappings. Use this to configure your application. The key has to correlate to the TYPE-Header of any incoming message,
     * the value is the name of the Consumer-Bean you want to route the message to.
     */
    @Nullable
    private Map<String, @NotBlank String> typeMappings;

}