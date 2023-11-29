package de.muenchen.oss.digiwf.dms.integration.application.service;

import de.muenchen.oss.digiwf.dms.integration.application.port.out.ReadContentPort;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.TransferContentPort;
import de.muenchen.oss.digiwf.dms.integration.domain.Content;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReadContentServiceTest {

    private final TransferContentPort transferContentPort = mock(TransferContentPort.class);

    private final ReadContentPort readContent = mock(ReadContentPort.class);

    private final ReadContentService readContentService = new ReadContentService(transferContentPort, readContent);

    @Test
    void readContent() {

        Content content = new Content("extension", "name", "content".getBytes());

        when(this.readContent.readContent(any(), any())).thenReturn(List.of(content));

        doNothing().when(transferContentPort).transferContent(any(), any(), any());

        readContentService.readContent(List.of("fileCoo"), "user", "filepath/", "filecontext/");

        verify(this.readContent, times(1)).readContent(List.of("fileCoo"), "user");

        verify(this.transferContentPort, times(1)).transferContent(List.of(content), "filepath/", "filecontext/");
    }
}