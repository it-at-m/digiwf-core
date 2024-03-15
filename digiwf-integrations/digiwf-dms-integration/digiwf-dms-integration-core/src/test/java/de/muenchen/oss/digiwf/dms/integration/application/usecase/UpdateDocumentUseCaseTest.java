package de.muenchen.oss.digiwf.dms.integration.application.usecase;

import de.muenchen.oss.digiwf.dms.integration.application.port.out.LoadFileOutPort;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.UpdateDocumentOutPort;
import de.muenchen.oss.digiwf.dms.integration.domain.Content;
import de.muenchen.oss.digiwf.dms.integration.domain.DocumentType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UpdateDocumentUseCaseTest {

    private final LoadFileOutPort loadFileOutPort = mock(LoadFileOutPort.class);

    private final UpdateDocumentOutPort updateDocumentOutPort = mock(UpdateDocumentOutPort.class);

    private final UpdateDocumentUseCase updateDocumentUseCase = new UpdateDocumentUseCase(updateDocumentOutPort, loadFileOutPort);

    @Test
    void updateDocument() {

        Content content = new Content("extension", "name", "content".getBytes());

        List<String> filepaths = List.of("path/content.pdf");

        when(this.loadFileOutPort.loadFiles(any(), any())).thenReturn(List.of(content));

        doNothing().when(updateDocumentOutPort).updateDocument(any(), any(), any(), any());

        updateDocumentUseCase.updateDocument("procedureCOO", "user", DocumentType.EINGEHEND, filepaths, "filecontext");

        verify(this.loadFileOutPort, times(1)).loadFiles(filepaths, "filecontext");

        verify(this.updateDocumentOutPort, times(1)).updateDocument("procedureCOO", DocumentType.EINGEHEND, List.of(content), "user");


    }
}