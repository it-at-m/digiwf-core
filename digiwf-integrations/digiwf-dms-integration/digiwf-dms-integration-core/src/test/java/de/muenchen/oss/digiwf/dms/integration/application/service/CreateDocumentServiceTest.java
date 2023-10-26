package de.muenchen.oss.digiwf.dms.integration.application.service;

import de.muenchen.oss.digiwf.dms.integration.application.port.out.CreateDocumentPort;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.LoadFilePort;
import de.muenchen.oss.digiwf.dms.integration.domain.Content;
import de.muenchen.oss.digiwf.dms.integration.domain.Document;
import de.muenchen.oss.digiwf.dms.integration.domain.DocumentType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateDocumentServiceTest {

    private final LoadFilePort loadFilePort = mock(LoadFilePort.class);

    private final CreateDocumentPort createDocumentPort = mock(CreateDocumentPort.class);

    private final CreateDocumentService createDocumentService = new CreateDocumentService(createDocumentPort, loadFilePort);

    @Test
    void createDocument() {

        Content content = new Content("extension", "name", "content".getBytes());

        List<String> filepaths = List.of("path/content.pdf");

        when(this.loadFilePort.loadFiles(any(), any())).thenReturn(List.of(content));

        when(this.createDocumentPort.createDocument(any(), any())).thenReturn("documentCOO");

        createDocumentService.createDocument("procedureCOO", "title", "user", DocumentType.EINGEHEND, filepaths, "filecontext");

        verify(this.loadFilePort, times(1)).loadFiles(filepaths, "filecontext");

        verify(this.createDocumentPort, times(1)).createDocument(new Document("procedureCOO", "title", DocumentType.EINGEHEND, List.of(content)), "user");

    }
}
