package de.muenchen.oss.digiwf.ticket.integration.adapter.out.zammad;

import de.muenchen.oss.digiwf.ticket.integration.adapter.zammad.api.TicketsApi;
import de.muenchen.oss.digiwf.ticket.integration.adapter.zammad.model.UpdateTicketArticleDTO;
import de.muenchen.oss.digiwf.ticket.integration.adapter.zammad.model.UpdateTicketDTO;
import de.muenchen.oss.digiwf.ticket.integration.application.port.out.TicketOutPort;
import de.muenchen.oss.digiwf.ticket.integration.domain.model.Article;
import de.muenchen.oss.digiwf.ticket.integration.domain.model.TicketStatus;
import lombok.RequiredArgsConstructor;
import lombok.val;


@RequiredArgsConstructor
public class ZammadAdapter implements TicketOutPort {

    private final TicketsApi ticketsApi;


    @Override
    public void updateTicket(String ticketId, Article article, TicketStatus status) {

        final var ticketUpdateDto = mapToDTO(ticketId, article, status);

        ticketsApi.updateTicket(ticketId, ticketUpdateDto, null, article.getUserId()).block();

    }

    private UpdateTicketDTO mapToDTO(String ticketId, Article article, TicketStatus status) {
        val ticketUpdateDto = new UpdateTicketDTO();
        // Note: TicketId is required in body and url
        ticketUpdateDto.setId(ticketId);
        ticketUpdateDto.setState(mapStatus(status));
        val articleDto = new UpdateTicketArticleDTO();
        articleDto.setBody(article.getText());
        ticketUpdateDto.setArticle(articleDto);
        return ticketUpdateDto;
    }

    private UpdateTicketDTO.StateEnum mapStatus(TicketStatus status) {
        if (status == null) {
            return null;
        }

        return switch (status) {
            case OPEN -> UpdateTicketDTO.StateEnum.OPEN;
            case CLOSED -> UpdateTicketDTO.StateEnum.CLOSED;
            case PENDING_CLOSE -> UpdateTicketDTO.StateEnum.PENDING_CLOSE;
            default -> null;
        };
    }
}
