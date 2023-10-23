package de.muenchen.oss.digiwf.dms.integration.application.service;

import de.muenchen.oss.digiwf.dms.integration.application.port.out.ReadFilesPort;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.TransferFilePort;
import de.muenchen.oss.digiwf.dms.integration.domain.File;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReadFilesServiceTest {

    private final TransferFilePort transferFilePort = mock(TransferFilePort.class);

    private final ReadFilesPort readFilesPort = mock(ReadFilesPort.class);

    private final ReadFilesService readFilesService = new ReadFilesService(transferFilePort, readFilesPort);

    @Test
    void readFiles() {

        File content = new File("extension", "name", "content".getBytes());

        when(this.readFilesPort.readFiles(any(), any())).thenReturn(List.of(content));

        doNothing().when(transferFilePort).transferFiles(any(), any(), any());

        readFilesService.readFiles(List.of("fileCoo"), "user", "filepath/", "filecontext/");

        verify(this.readFilesPort, times(1)).readFiles(List.of("fileCoo"), "user");

        verify(this.transferFilePort, times(1)).transferFiles(List.of(content), "filepath/", "filecontext/");
    }
}