package io.muenchendigital.digiwf.okewo.integration.api.configuration;

import io.muenchendigital.digiwf.okewo.integration.api.streaming.OkEwoStreamingEventListener;
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
public class OkEwoConfiguration {

    private static final String HEADER_GET_PERSON = "getPerson";

    private static final String HEADER_SEARCH_PERSON = "searchPerson";

    private static final String HEADER_GET_PERSON_ERWEITERT = "getPersonErweitert";

    private static final String HEADER_SEARCH_PERSON_ERWEITERT = "searchPersonErweitert";

    /**
     * Bean of type {@link RoutingCallback} to register the routes
     * defined in {@link OkEwoStreamingEventListener} programmatically.
     *
     * @return the {@link RoutingCallback} as a bean.
     */
    @Bean
    @ConditionalOnMissingBean
    public MessageRoutingCallback okEwoRouter() {
        final Map<String, String> typeMappings = new HashMap<>();
        typeMappings.put(HEADER_GET_PERSON, HEADER_GET_PERSON);
        typeMappings.put(HEADER_SEARCH_PERSON, HEADER_SEARCH_PERSON);
        typeMappings.put(HEADER_GET_PERSON_ERWEITERT, HEADER_GET_PERSON_ERWEITERT);
        typeMappings.put(HEADER_SEARCH_PERSON_ERWEITERT, HEADER_SEARCH_PERSON_ERWEITERT);
        return new RoutingCallback(typeMappings);
    }

}
