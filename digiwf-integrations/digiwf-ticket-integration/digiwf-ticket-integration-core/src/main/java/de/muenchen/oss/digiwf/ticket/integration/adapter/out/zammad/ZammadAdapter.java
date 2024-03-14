package de.muenchen.oss.digiwf.ticket.integration.adapter.out.zammad;

import de.muenchen.oss.digiwf.ticket.integration.adapter.zammad.api.TicketsApi;
import de.muenchen.oss.digiwf.ticket.integration.adapter.zammad.model.AttachmentDTO;
import de.muenchen.oss.digiwf.ticket.integration.adapter.zammad.model.UpdateTicketArticleDTO;
import de.muenchen.oss.digiwf.ticket.integration.adapter.zammad.model.UpdateTicketDTO;
import de.muenchen.oss.digiwf.ticket.integration.application.port.out.TicketOutPort;
import de.muenchen.oss.digiwf.ticket.integration.domain.model.Article;
import de.muenchen.oss.digiwf.ticket.integration.domain.model.FileContent;
import de.muenchen.oss.digiwf.ticket.integration.domain.model.TicketStatus;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
public class ZammadAdapter implements TicketOutPort {

    private final TicketsApi ticketsApi;


    @Override
    public void updateTicket(String ticketId, Article article, TicketStatus status, List<FileContent> attachments) {

        final var ticketUpdateDto = mapToDTO(ticketId, article, status, attachments);

        ticketsApi.updateTicket(ticketId, ticketUpdateDto, null, article.getUserId()).block();

    }

    private UpdateTicketDTO mapToDTO(String ticketId, Article article, TicketStatus status, List<FileContent> attachments) {
        val ticketUpdateDto = new UpdateTicketDTO();
        // Note: TicketId is required in body and url
        ticketUpdateDto.setId(ticketId);
        ticketUpdateDto.setState(mapStatus(status));
        val articleDto = new UpdateTicketArticleDTO();
        articleDto.setBody(article.getText());
        ticketUpdateDto.setArticle(articleDto);
        // attachments
        final List<AttachmentDTO> attachmentDTOS = new ArrayList<>();
        attachments.forEach(file -> {
            val attachmentDTO = new AttachmentDTO();
            attachmentDTO.setFilename(file.getName());
            attachmentDTO.setMimeType(file.getMimeType());
            // file attachments have to be base64 encoded
            final String base64Data = java.util.Base64.getEncoder().encodeToString(file.getData());
            attachmentDTO.setData(base64Data);
            attachmentDTOS.add(attachmentDTO);
        });
        if (!attachmentDTOS.isEmpty()) {
            articleDto.setAttachments(attachmentDTOS);
        }
        return ticketUpdateDto;
    }

    private UpdateTicketDTO.StateEnum mapStatus(TicketStatus status) {
        if (status == null) {
            return null;
        }

        return switch (status) {
            case OPEN -> UpdateTicketDTO.StateEnum.OPEN;
            case CLOSED -> UpdateTicketDTO.StateEnum.CLOSED;
        };
    }
}
