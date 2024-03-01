/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf.ticket.integration.configuration;

import de.muenchen.oss.digiwf.ticket.integration.adapter.out.zammad.ZammadAdapter;
import de.muenchen.oss.digiwf.ticket.integration.adapter.zammad.api.TicketsApi;
import de.muenchen.oss.digiwf.ticket.integration.application.WriteArticleUseCase;
import de.muenchen.oss.digiwf.ticket.integration.application.port.in.WriteArticleInPort;
import de.muenchen.oss.digiwf.ticket.integration.application.port.out.TicketOutPort;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({
})
public class TicketIntegrationAutoConfiguration {


    @Bean
    public TicketOutPort ticketOutPort(final TicketsApi ticketsApi) {
        return new ZammadAdapter(ticketsApi);
    }

    @Bean
    public WriteArticleInPort writeArticleUseCase(TicketOutPort ticketOutPort) {
        return new WriteArticleUseCase(ticketOutPort);
    }

}
