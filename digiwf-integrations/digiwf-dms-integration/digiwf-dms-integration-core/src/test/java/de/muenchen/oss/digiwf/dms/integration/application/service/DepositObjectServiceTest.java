package de.muenchen.oss.digiwf.dms.integration.application.service;

import de.muenchen.oss.digiwf.dms.integration.application.port.out.DepositObjectPort;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DepositObjectServiceTest {

    private final DepositObjectPort depositObjectPort = mock(DepositObjectPort.class);

    private final DepositObjectService depositObjectService = new DepositObjectService(depositObjectPort);

    @Test
    void depositObject() {

        doNothing().when(depositObjectPort).depositObject(any(), any());

        depositObjectService.depositObject("objectCoo", "user");

        verify(this.depositObjectPort, times(1)).depositObject("objectCoo", "user");
    }


}
