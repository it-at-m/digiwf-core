package de.muenchen.oss.digiwf.ticket.integration.application;

import de.muenchen.oss.digiwf.ticket.integration.application.port.out.TicketOutPort;
import de.muenchen.oss.digiwf.ticket.integration.domain.model.Article;
import de.muenchen.oss.digiwf.ticket.integration.domain.model.TicketStatus;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

public class WriteArticleUseCaseTest {

    private final TicketOutPort ticketOutPort = Mockito.mock(TicketOutPort.class);

    private final WriteArticleUseCase writeArticleUseCase = new WriteArticleUseCase(ticketOutPort);

    @Test
    void writeArticle_callsUpdateTicketWithCorrectArguments() {
        // given
        String ticketId = "123";
        Article article = new Article("article", "user");
        TicketStatus status = TicketStatus.OPEN;

        // when
        writeArticleUseCase.writeArticle(ticketId, article, status);

        // then
        verify(ticketOutPort).updateTicket(eq(ticketId), eq(article), eq(status));
    }

}
