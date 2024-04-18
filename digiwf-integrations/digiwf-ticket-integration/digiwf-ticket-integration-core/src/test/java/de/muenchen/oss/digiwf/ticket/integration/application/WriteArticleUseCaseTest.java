package de.muenchen.oss.digiwf.ticket.integration.application;

import de.muenchen.oss.digiwf.ticket.integration.application.port.out.LoadFileOutPort;
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
    private final LoadFileOutPort loadFileOutPort = Mockito.mock(LoadFileOutPort.class);

    private final WriteArticleUseCase writeArticleUseCase = new WriteArticleUseCase(ticketOutPort, loadFileOutPort);

    @Test
    void writeArticle_callsUpdateTicketWithCorrectArguments() {
        // given
        final String ticketId = "123";
        final Article article = new Article("article", "user");
        final TicketStatus status = TicketStatus.OPEN;

        // when
        when(loadFileOutPort.loadFiles(List.of(), "fileContext", "processDefinition")).thenReturn(List.of());

        writeArticleUseCase.writeArticle(ticketId, article, status, List.of(), "fileContext", "processDefinition");

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
        when(loadFileOutPort.loadFiles(filepaths, "fileContext", "processDefinition")).thenReturn(
                List.of(fileContent));

        writeArticleUseCase.writeArticle(ticketId, article, status, filepaths, "fileContext", "processDefinition");

        // then
        verify(ticketOutPort).updateTicket(eq(ticketId), eq(article), eq(status), eq(List.of(fileContent)));
    }

}
