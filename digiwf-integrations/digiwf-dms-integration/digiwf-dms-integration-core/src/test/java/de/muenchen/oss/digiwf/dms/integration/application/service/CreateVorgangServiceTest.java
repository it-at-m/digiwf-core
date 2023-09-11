package de.muenchen.oss.digiwf.dms.integration.application.service;

import de.muenchen.oss.digiwf.dms.integration.application.port.out.VorgangRepository;
import de.muenchen.oss.digiwf.dms.integration.domain.Procedure;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateVorgangServiceTest {

    private final VorgangRepository vorgangRepository = mock(VorgangRepository.class);

    private final CreateVorgangService createVorgangService = new CreateVorgangService(vorgangRepository);

    @Test
    void createVorgang() {

        when(this.vorgangRepository.createVorgang(any(), any())).thenReturn(new Procedure("fileCOO", "title"));

        createVorgangService.createVorgang("title", "fileCOO", "user");

        verify(this.vorgangRepository, times(1)).createVorgang(new Procedure("coo", "fileCOO"), "user");
    }


}
