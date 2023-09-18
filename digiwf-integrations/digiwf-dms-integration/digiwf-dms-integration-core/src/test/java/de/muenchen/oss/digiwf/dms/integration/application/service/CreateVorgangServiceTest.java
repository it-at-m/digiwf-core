package de.muenchen.oss.digiwf.dms.integration.application.service;

import de.muenchen.oss.digiwf.dms.integration.application.port.out.ProcedureRepository;
import de.muenchen.oss.digiwf.dms.integration.domain.Procedure;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateVorgangServiceTest {

    private final ProcedureRepository vorgangRepository = mock(ProcedureRepository.class);

    private final CreateProcedureService createVorgangService = new CreateProcedureService(vorgangRepository);

    @Test
    void createVorgang() {

        when(this.vorgangRepository.createProcedure(any(), any())).thenReturn(new Procedure("fileCOO", "title"));

        createVorgangService.createProcedure("title", "fileCOO", "user");

        verify(this.vorgangRepository, times(1)).createProcedure(new Procedure("fileCOO", "title"), "user");
    }


}
