package de.muenchen.oss.digiwf.dms.integration.application.service;

import de.muenchen.oss.digiwf.dms.integration.application.port.out.ProcedureRepository;
import de.muenchen.oss.digiwf.dms.integration.domain.Procedure;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateProcedureServiceTest {

    private final ProcedureRepository procedureRepository = mock(ProcedureRepository.class);

    private final CreateProcedureService createProcedureService = new CreateProcedureService(procedureRepository);

    @Test
    void createProcedure() {

        when(this.procedureRepository.createProcedure(any(), any())).thenReturn(new Procedure("fileCOO", "title"));

        createProcedureService.createProcedure("title", "fileCOO", "user");

        verify(this.procedureRepository, times(1)).createProcedure(new Procedure("fileCOO", "title"), "user");
    }


}
