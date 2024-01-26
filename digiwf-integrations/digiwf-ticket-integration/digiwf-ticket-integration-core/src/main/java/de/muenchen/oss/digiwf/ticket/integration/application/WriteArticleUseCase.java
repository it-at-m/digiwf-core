package de.muenchen.oss.digiwf.ticket.integration.application;

import de.muenchen.oss.digiwf.ticket.integration.application.port.in.WriteArticleInPort;
import de.muenchen.oss.digiwf.ticket.integration.application.port.out.TicketOutPort;
import de.muenchen.oss.digiwf.ticket.integration.domain.model.Article;
import de.muenchen.oss.digiwf.ticket.integration.domain.model.TicketStatus;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Validated
@RequiredArgsConstructor
public class WriteArticleUseCase implements WriteArticleInPort {

    private final TicketOutPort ticketOutPort;

    @Override
    public void writeArticle(@NotBlank String ticketId, @NotBlank Article article, @Nullable TicketStatus status) {
        ticketOutPort.updateTicket(ticketId, article, status);
    }
}
