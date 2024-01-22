/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf.ticket.integration.configuration;

import de.muenchen.oss.digiwf.ticket.integration.adapter.out.zammad.ZammadAdapter;
import de.muenchen.oss.digiwf.ticket.integration.adapter.zammad.api.ArticlesApi;
import de.muenchen.oss.digiwf.ticket.integration.application.WriteArticleUseCase;
import de.muenchen.oss.digiwf.ticket.integration.application.port.out.TicketOutPort;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({
        TicketIntegrationProperties.class,
})
public class TicketIntegrationAutoConfiguration {

    private final TicketIntegrationProperties ticketIntegrationProperties;

    @Bean
    public TicketOutPort ticketOutPort(final ArticlesApi articlesApi) {
        return new ZammadAdapter(articlesApi);
    }

    @Bean
    public WriteArticleUseCase writeArticleUseCase(TicketOutPort ticketOutPort) {
        return new WriteArticleUseCase(ticketOutPort);
    }

}
