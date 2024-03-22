package de.muenchen.oss.digiwf.dms.integration.application.usecase;

import de.muenchen.oss.digiwf.dms.integration.application.port.out.CreateDocumentOutPort;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.LoadFileOutPort;
import de.muenchen.oss.digiwf.dms.integration.domain.Content;
import de.muenchen.oss.digiwf.dms.integration.domain.Document;
import de.muenchen.oss.digiwf.dms.integration.domain.DocumentType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateDocumentUseCaseTest {

    private final LoadFileOutPort loadFileOutPort = mock(LoadFileOutPort.class);

    private final CreateDocumentOutPort createDocumentOutPort = mock(CreateDocumentOutPort.class);

    private final CreateDocumentUseCase createDocumentUseCase = new CreateDocumentUseCase(createDocumentOutPort, loadFileOutPort);

    @Test
    void createDocument() {

        Content content = new Content("extension", "name", "content".getBytes());

        List<String> filepaths = List.of("path/content.pdf");

        when(this.loadFileOutPort.loadFiles(any(), any())).thenReturn(List.of(content));

        when(this.createDocumentOutPort.createDocument(any(), any())).thenReturn("documentCOO");
        LocalDate testDate = LocalDate.parse("2023-12-01");

        createDocumentUseCase.createDocument("procedureCOO", "title", testDate, "user", DocumentType.EINGEHEND, filepaths, "filecontext");

        verify(this.loadFileOutPort, times(1)).loadFiles(filepaths, "filecontext");

        verify(this.createDocumentOutPort, times(1)).createDocument(new Document("procedureCOO", "title", testDate, DocumentType.EINGEHEND, List.of(content)), "user");

    }
}
