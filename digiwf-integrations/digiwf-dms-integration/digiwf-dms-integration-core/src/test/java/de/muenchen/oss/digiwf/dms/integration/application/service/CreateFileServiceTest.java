package de.muenchen.oss.digiwf.dms.integration.application.service;

import de.muenchen.oss.digiwf.dms.integration.application.port.out.CreateFilePort;
import de.muenchen.oss.digiwf.dms.integration.domain.File;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateFileServiceTest {

    private final CreateFilePort createFilePort = mock(CreateFilePort.class);

    private final CreateFileService createFileService = new CreateFileService(createFilePort);

    @Test
    void createFile() {

        when(this.createFilePort.createFile(any(), any())).thenReturn("fileCOO");

        createFileService.createFile("title", "apentryCOO", "user");

        verify(this.createFilePort, times(1)).createFile(new File("apentryCOO", "title"), "user");

    }
}