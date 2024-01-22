package de.muenchen.oss.digiwf.ticket.integration.application;

import de.muenchen.oss.digiwf.ticket.integration.application.port.in.WriteArticleInPort;
import de.muenchen.oss.digiwf.ticket.integration.application.port.out.TicketOutPort;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.validation.annotation.Validated;

@Validated
@RequiredArgsConstructor
public class WriteArticleUseCase implements WriteArticleInPort {

    private final TicketOutPort ticketOutPort;

    @Override
    public void writeArticle(@NotBlank String ticketId, @NotBlank String article, @Nullable String status) {

        val ticket = ticketOutPort.getTicket(ticketId);
        ticket.setArticle(article);

        if (status != null) {
            ticket.updateStatus(status);
        }

        ticketOutPort.updateTicket(ticket);

    }
}
