package de.muenchen.oss.digiwf.dms.integration.application.service;

import de.muenchen.oss.digiwf.dms.integration.application.port.out.LoadFilePort;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.UpdateDocumentPort;
import de.muenchen.oss.digiwf.dms.integration.domain.Content;
import de.muenchen.oss.digiwf.dms.integration.domain.DocumentType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UpdateDocumentServiceTest {

    private final LoadFilePort loadFilePort = mock(LoadFilePort.class);

    private final UpdateDocumentPort updateDocumentPort = mock(UpdateDocumentPort.class);

    private final UpdateDocumentService updateDocumentService = new UpdateDocumentService(updateDocumentPort, loadFilePort);

    @Test
    void updateDocument() {

        Content content = new Content("extension", "name", "content".getBytes());

        List<String> filepaths = List.of("path/content.pdf");

        when(this.loadFilePort.loadFiles(any(), any())).thenReturn(List.of(content));

        doNothing().when(updateDocumentPort).updateDocument(any(), any(), any(), any());

        updateDocumentService.updateDocument("procedureCOO", "user", DocumentType.EINGEHEND, filepaths, "filecontext");

        verify(this.loadFilePort, times(1)).loadFiles(filepaths, "filecontext");

        verify(this.updateDocumentPort, times(1)).updateDocument("procedureCOO", DocumentType.EINGEHEND, List.of(content), "user");


    }
}