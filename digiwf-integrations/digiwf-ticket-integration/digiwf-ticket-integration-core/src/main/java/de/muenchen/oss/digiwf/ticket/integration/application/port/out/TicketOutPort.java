package de.muenchen.oss.digiwf.ticket.integration.application.port.out;

import de.muenchen.oss.digiwf.ticket.integration.domain.model.Article;
import de.muenchen.oss.digiwf.ticket.integration.domain.model.TicketStatus;

public interface TicketOutPort {

    void updateTicket(String ticketId, Article article, TicketStatus status);

}
