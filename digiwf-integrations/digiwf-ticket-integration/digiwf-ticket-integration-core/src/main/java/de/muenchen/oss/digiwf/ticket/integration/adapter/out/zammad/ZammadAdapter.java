package de.muenchen.oss.digiwf.ticket.integration.adapter.out.zammad;

import de.muenchen.oss.digiwf.ticket.integration.adapter.zammad.api.ArticlesApi;
import de.muenchen.oss.digiwf.ticket.integration.application.port.out.TicketOutPort;
import de.muenchen.oss.digiwf.ticket.integration.domain.model.Ticket;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class ZammadAdapter implements TicketOutPort {

    private final ArticlesApi articlesApi;


    @Override
    public void updateTicket(Ticket ticket) {

    }

    @Override
    public Ticket getTicket(String ticketId) {
        return null;
    }
}
