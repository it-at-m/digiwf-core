package de.muenchen.oss.digiwf.ticket.integration.application;

import de.muenchen.oss.digiwf.ticket.integration.application.port.out.LoadFilePort;
import de.muenchen.oss.digiwf.ticket.integration.application.port.out.TicketOutPort;
import de.muenchen.oss.digiwf.ticket.integration.domain.model.Article;
import de.muenchen.oss.digiwf.ticket.integration.domain.model.FileContent;
import de.muenchen.oss.digiwf.ticket.integration.domain.model.TicketStatus;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class WriteArticleUseCaseTest {

    private final TicketOutPort ticketOutPort = Mockito.mock(TicketOutPort.class);
    private final LoadFilePort loadFilePort = Mockito.mock(LoadFilePort.class);

    private final WriteArticleUseCase writeArticleUseCase = new WriteArticleUseCase(ticketOutPort, loadFilePort);

    @Test
    void writeArticle_callsUpdateTicketWithCorrectArguments() {
        // given
        final String ticketId = "123";
        final Article article = new Article("article", "user");
        final TicketStatus status = TicketStatus.OPEN;

        // when
        when(loadFilePort.loadFiles(List.of(), "processDefinition")).thenReturn(List.of());

        writeArticleUseCase.writeArticle(ticketId, article, status, List.of(), "processDefinition");

        // then
        verify(ticketOutPort).updateTicket(eq(ticketId), eq(article), eq(status), eq(List.of()));
    }

    @Test
    void writeArticle_callsUpdateTicketWithCorrectArgumentsAndFilePaths() {
        // given
        final String ticketId = "123";
        final Article article = new Article("article", "user");
        final TicketStatus status = TicketStatus.OPEN;
        final List<String> filepaths = List.of("test/file/path");

        final FileContent fileContent = new FileContent("text/plain", "file", "content".getBytes());

        // when
        when(loadFilePort.loadFiles(filepaths, "processDefinition")).thenReturn(
                List.of(fileContent));

        writeArticleUseCase.writeArticle(ticketId, article, status, filepaths, "processDefinition");

        // then
        verify(ticketOutPort).updateTicket(eq(ticketId), eq(article), eq(status), eq(List.of(fileContent)));
    }

}
