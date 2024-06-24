package de.muenchen.oss.digiwf.dms.integration.application.usecase;

import de.muenchen.oss.digiwf.dms.integration.application.port.out.ListContentOutPort;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.LoadFileOutPort;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.UpdateDocumentOutPort;
import de.muenchen.oss.digiwf.dms.integration.domain.Content;
import de.muenchen.oss.digiwf.dms.integration.domain.DocumentResponse;
import de.muenchen.oss.digiwf.dms.integration.domain.DocumentType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UpdateDocumentUseCaseTest {

    private final LoadFileOutPort loadFileOutPort = mock(LoadFileOutPort.class);
    private final ListContentOutPort listContentOutPort = mock(ListContentOutPort.class);

    private final UpdateDocumentOutPort updateDocumentOutPort = mock(UpdateDocumentOutPort.class);

    private final UpdateDocumentUseCase updateDocumentUseCase = new UpdateDocumentUseCase(updateDocumentOutPort, listContentOutPort, loadFileOutPort);

    @Test
    void updateDocument() {

        Content content = new Content("extension", "name", "content".getBytes());

        List<String> filepaths = List.of("path/content.pdf");
        String docCoo = "documentCOO";
        String user = "user";
        List<String> fileCoos = List.of("contentCoo1", "contentCoo2");

        when(this.loadFileOutPort.loadFiles(any(), any(), any())).thenReturn(List.of(content));
        when(this.listContentOutPort.listContentCoos(docCoo, user)).thenReturn(fileCoos);

        doNothing().when(updateDocumentOutPort).updateDocument(any(), any(), any(), any());

        DocumentResponse documentResponse = updateDocumentUseCase.updateDocument(docCoo, "user", DocumentType.EINGEHEND, filepaths, "filecontext", "processDefinitionId");

        assertEquals(docCoo, documentResponse.getDocumentCoo());
        assertEquals(fileCoos, documentResponse.getContentCoos());
        verify(this.loadFileOutPort, times(1)).loadFiles(filepaths, "filecontext", "processDefinitionId");
        verify(this.updateDocumentOutPort, times(1)).updateDocument(docCoo, DocumentType.EINGEHEND, List.of(content), "user");

    }
}