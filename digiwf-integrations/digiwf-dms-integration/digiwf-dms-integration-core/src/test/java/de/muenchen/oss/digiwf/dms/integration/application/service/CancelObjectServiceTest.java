package de.muenchen.oss.digiwf.dms.integration.application.service;

import de.muenchen.oss.digiwf.dms.integration.application.port.out.CancelObjectPort;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CancelObjectServiceTest {

    private final CancelObjectPort cancelObjectPort = mock(CancelObjectPort.class);

    private final CancelObjectService cancelObjectService = new CancelObjectService(cancelObjectPort);

    @Test
    void cancelObject() {

        doNothing().when(cancelObjectPort).cancelObject(any(), any());

        cancelObjectService.cancelObject("objectCoo", "user");

        verify(this.cancelObjectPort, times(1)).cancelObject("objectCoo", "user");
    }


}
