package de.muenchen.oss.digiwf.ticket.integration.adapter.out.zammad;

import de.muenchen.oss.digiwf.ticket.integration.adapter.zammad.api.TicketsApi;
import de.muenchen.oss.digiwf.ticket.integration.adapter.zammad.model.UpdateTicketArticleDTO;
import de.muenchen.oss.digiwf.ticket.integration.adapter.zammad.model.UpdateTicketDTO;
import de.muenchen.oss.digiwf.ticket.integration.domain.model.Article;
import de.muenchen.oss.digiwf.ticket.integration.domain.model.TicketStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ZammadAdapterTest {

    private TicketsApi ticketsApi = Mockito.mock(TicketsApi.class);

    private final ZammadAdapter zammadAdapter = new ZammadAdapter(ticketsApi);

    @BeforeEach
    void setUp() {
        when(ticketsApi.updateTicket(any(), any(), any(), any())).thenReturn(Mono.empty());
    }

    @Test
    void testUpdateTicket() {
        // given
        String ticketId = "123";
        Article article = new Article("mein text", "123");
        TicketStatus status = TicketStatus.OPEN;

        // when
        zammadAdapter.updateTicket(ticketId, article, status);

        // then
        UpdateTicketDTO updateTicketDTO = new UpdateTicketDTO();
        updateTicketDTO.setId("123");
        UpdateTicketArticleDTO articleDTO = new UpdateTicketArticleDTO();
        articleDTO.setBody("mein text");
        updateTicketDTO.setArticle(articleDTO);
        updateTicketDTO.setState(UpdateTicketDTO.StateEnum.OPEN);
        verify(ticketsApi).updateTicket(eq("123"), eq(updateTicketDTO), isNull(), eq("123"));
    }
}
