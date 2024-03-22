package de.muenchen.oss.digiwf.dms.integration.application.usecase;

import de.muenchen.oss.digiwf.dms.integration.application.port.out.CreateFileOutPort;
import de.muenchen.oss.digiwf.dms.integration.domain.File;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateFileUseCaseTest {

    private final CreateFileOutPort createFileOutPort = mock(CreateFileOutPort.class);

    private final CreateFileUseCase createFileUseCase = new CreateFileUseCase(createFileOutPort);

    @Test
    void createFile() {

        when(this.createFileOutPort.createFile(any(), any())).thenReturn("fileCOO");

        createFileUseCase.createFile("title", "apentryCOO", "user");

        verify(this.createFileOutPort, times(1)).createFile(new File("apentryCOO", "title"), "user");

    }
}