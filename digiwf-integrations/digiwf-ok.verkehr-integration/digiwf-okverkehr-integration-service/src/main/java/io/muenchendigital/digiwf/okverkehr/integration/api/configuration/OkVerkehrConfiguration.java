package io.muenchendigital.digiwf.okverkehr.integration.api.configuration;

import io.muenchendigital.digiwf.okverkehr.integration.api.streaming.OkVerkehrStreamingEventListener;
import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.infrastructure.RoutingCallback;
import io.muenchendigital.digiwf.spring.cloudstream.utils.configuration.StreamingConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.function.context.MessageRoutingCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@AutoConfigureBefore({StreamingConfiguration.class})
public class OkVerkehrConfiguration {

    private static final String HEADER_GET_HALTER = "getHalter";

    /**
     * Bean of type {@link RoutingCallback} to register the routes
     * defined in {@link OkVerkehrStreamingEventListener} programmatically.
     *
     * @return the {@link RoutingCallback} as a bean.
     */
    @Bean
    @ConditionalOnMissingBean
    public MessageRoutingCallback okVerkehrRouter() {
        final Map<String, String> typeMappings = new HashMap<>();
        typeMappings.put(HEADER_GET_HALTER, HEADER_GET_HALTER);
        return new RoutingCallback(typeMappings);
    }


}
