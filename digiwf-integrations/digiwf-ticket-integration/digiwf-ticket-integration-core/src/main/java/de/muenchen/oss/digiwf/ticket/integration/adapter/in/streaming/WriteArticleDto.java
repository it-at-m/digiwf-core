package de.muenchen.oss.digiwf.ticket.integration.adapter.in.streaming;

import de.muenchen.oss.digiwf.ticket.integration.domain.model.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WriteArticleDto {

    private String ticketId;
    private String article;
    private String userId;
    private TicketStatus status;

}
