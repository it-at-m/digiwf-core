package de.muenchen.oss.digiwf.ticket.integration.application;

import de.muenchen.oss.digiwf.ticket.integration.application.port.in.WriteArticleInPort;
import de.muenchen.oss.digiwf.ticket.integration.application.port.out.LoadFilePort;
import de.muenchen.oss.digiwf.ticket.integration.application.port.out.TicketOutPort;
import de.muenchen.oss.digiwf.ticket.integration.domain.model.Article;
import de.muenchen.oss.digiwf.ticket.integration.domain.model.FileContent;
import de.muenchen.oss.digiwf.ticket.integration.domain.model.TicketStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@RequiredArgsConstructor
public class WriteArticleUseCase implements WriteArticleInPort {

    private final TicketOutPort ticketOutPort;
    private final LoadFilePort loadFilePort;

    @Override
    public void writeArticle(@NotBlank String ticketId, @NotNull Article article, TicketStatus status, final List<String> filepaths, final String processDefinition) {
        if ((filepaths != null) && !filepaths.isEmpty()) {
            List<FileContent> fileContents = loadFilePort.loadFiles(filepaths, processDefinition);
            ticketOutPort.updateTicket(ticketId, article, status, fileContents);
            return;
        }
        ticketOutPort.updateTicket(ticketId, article, status, List.of());
    }
}
