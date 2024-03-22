package de.muenchen.oss.digiwf.ticket.integration.application.port.out;

import de.muenchen.oss.digiwf.ticket.integration.domain.model.Article;
import de.muenchen.oss.digiwf.ticket.integration.domain.model.FileContent;
import de.muenchen.oss.digiwf.ticket.integration.domain.model.TicketStatus;

import java.util.List;

public interface TicketOutPort {

    void updateTicket(String ticketId, Article article, TicketStatus status, List<FileContent> attachments);

}
