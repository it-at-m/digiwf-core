/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf.zammad;

import de.muenchen.oss.digiwf.ticket.integration.adapter.zammad.ApiClient;
import de.muenchen.oss.digiwf.ticket.integration.adapter.zammad.api.ArticlesApi;
import de.muenchen.oss.digiwf.ticket.integration.adapter.zammad.api.TicketsApi;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({
        ZammadProperties.class,
})
@PropertySource(value = "classpath:digiwf-zammad-application.yaml", factory = YamlPropertySourceFactory.class)
public class ZammadAutoConfiguration {

    private final ZammadProperties zammadProperties;

    @Bean
    public ArticlesApi articlesApi(final ApiClient apiClient) {
        return new ArticlesApi(apiClient);
    }

    @Bean
    public TicketsApi ticketsApi(final ApiClient apiClient) {
        return new TicketsApi(apiClient);
    }

    @Bean
    public ApiClient zammadApiClient(final ClientRegistrationRepository clientRegistrationRepository,
                                     final OAuth2AuthorizedClientService authorizedClientService) {
        final ApiClient apiClient = new ApiClient(this.webClient(clientRegistrationRepository, authorizedClientService));
        apiClient.setBasePath(this.zammadProperties.getBaseurl());
        return apiClient;
    }

    private WebClient webClient(
            final ClientRegistrationRepository clientRegistrationRepository,
            final OAuth2AuthorizedClientService authorizedClientService
    ) {
        final var oauth = new ServletOAuth2AuthorizedClientExchangeFilterFunction(
                new AuthorizedClientServiceOAuth2AuthorizedClientManager(
                        clientRegistrationRepository, authorizedClientService
                )
        );
        oauth.setDefaultClientRegistrationId("zammad");
        return WebClient.builder()
                .baseUrl(this.zammadProperties.getBaseurl())
                .apply(oauth.oauth2Configuration())
                .build();
    }


}
