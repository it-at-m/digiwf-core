package de.muenchen.oss.digiwf.dms.integration.application.service;

import de.muenchen.oss.digiwf.dms.integration.application.port.out.CreateProcedurePort;
import de.muenchen.oss.digiwf.dms.integration.domain.Procedure;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateProcedureServiceTest {

    private final CreateProcedurePort createProcedurePort = mock(CreateProcedurePort.class);

    private final CreateProcedureService createProcedureService = new CreateProcedureService(createProcedurePort);

    @Test
    void createProcedure() {

        when(this.createProcedurePort.createProcedure(any(), any())).thenReturn(new Procedure("fileCOO", "title"));

        createProcedureService.createProcedure("title", "fileCOO", "user");

        verify(this.createProcedurePort, times(1)).createProcedure(new Procedure("fileCOO", "title"), "user");
    }


}
