package de.muenchen.oss.digiwf.ticket.integration.application.port.out;

import de.muenchen.oss.digiwf.ticket.integration.domain.model.Ticket;

public interface TicketOutPort {

    void updateTicket(Ticket ticket);

    Ticket getTicket(String ticketId);

}
