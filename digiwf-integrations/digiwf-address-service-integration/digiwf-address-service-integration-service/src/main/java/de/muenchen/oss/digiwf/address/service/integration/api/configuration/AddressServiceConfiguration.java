package de.muenchen.oss.digiwf.address.service.integration.api.configuration;

import de.muenchen.oss.digiwf.address.service.integration.api.streaming.AddressServiceStreamingEventListener;
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
public class AddressServiceConfiguration {

    private static final String HEADER_SEARCH_ADRESSEN_BUNDESWEIT = "searchAdressenBundesweit";

    private static final String HEADER_CHECK_ADRESSE_MUENCHEN = "checkAdresseMuenchen";

    private static final String HEADER_LIST_ADRESSEN_MUENCHEN = "listAdressenMuenchen";

    private static final String HEADER_LIST_AENDERUNGEN_MUENCHEN = "listAenderungenMuenchen";

    private static final String HEADER_SEARCH_ADRESSEN_MUENCHEN = "searchAdressenMuenchen";

    private static final String HEADER_SEARCH_ADRESSEN_GEO_MUENCHEN = "searchAdressenGeoMuenchen";

    private static final String HEADER_FIND_STRASSE_BY_ID_MUENCHEN = "findStrasseByIdMuenchen";

    private static final String HEADER_LIST_STRASSE_MUENCHEN = "listStrassenMuenchen";


    /**
     * Bean of type {@link RoutingCallback} to register the routes
     * defined in {@link AddressServiceStreamingEventListener} programmatically.
     *
     * @return the {@link RoutingCallback} as a bean.
     */
    @Bean
    @ConditionalOnMissingBean
    public MessageRoutingCallback okEwoRouter() {
        final Map<String, String> typeMappings = new HashMap<>();
        typeMappings.put(HEADER_SEARCH_ADRESSEN_BUNDESWEIT, HEADER_SEARCH_ADRESSEN_BUNDESWEIT);
        typeMappings.put(HEADER_CHECK_ADRESSE_MUENCHEN, HEADER_CHECK_ADRESSE_MUENCHEN);
        typeMappings.put(HEADER_LIST_ADRESSEN_MUENCHEN, HEADER_LIST_ADRESSEN_MUENCHEN);
        typeMappings.put(HEADER_LIST_AENDERUNGEN_MUENCHEN, HEADER_LIST_AENDERUNGEN_MUENCHEN);
        typeMappings.put(HEADER_SEARCH_ADRESSEN_MUENCHEN, HEADER_SEARCH_ADRESSEN_MUENCHEN);
        typeMappings.put(HEADER_SEARCH_ADRESSEN_GEO_MUENCHEN, HEADER_SEARCH_ADRESSEN_GEO_MUENCHEN);
        typeMappings.put(HEADER_FIND_STRASSE_BY_ID_MUENCHEN, HEADER_FIND_STRASSE_BY_ID_MUENCHEN);
        typeMappings.put(HEADER_LIST_STRASSE_MUENCHEN, HEADER_LIST_STRASSE_MUENCHEN);
        return new RoutingCallback(typeMappings);
    }

}
