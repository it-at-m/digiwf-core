package de.muenchen.oss.digiwf.ocecosmos.integration.api.configuration;

import de.muenchen.oss.digiwf.ocecosmos.integration.api.streaming.OceCosmosStreamingEventListener;
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
public class OceCosmosConfiguration {

    private static final String HEADER_PRINT_REQUEST = "requestPrint";
    private static final String HEADER_PDF_PRINT_REQUEST = "requestPDFPrint";

    /**
     * Bean of type {@link RoutingCallback} to register the routes
     * defined in {@link OceCosmosStreamingEventListener} programmatically.
     *
     * @return the {@link RoutingCallback} as a bean.
     */
    @Bean
    @ConditionalOnMissingBean
    public MessageRoutingCallback oceCosmosRouter() {
        final Map<String, String> typeMappings = new HashMap<>();
        typeMappings.put(HEADER_PRINT_REQUEST, HEADER_PRINT_REQUEST);
        typeMappings.put(HEADER_PDF_PRINT_REQUEST, HEADER_PDF_PRINT_REQUEST);
        return new RoutingCallback(typeMappings);
    }

}
