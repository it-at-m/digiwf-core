package de.muenchen.oss.digiwf.dms.integration.application.usecase;

import de.muenchen.oss.digiwf.dms.integration.application.port.out.CancelObjectOutPort;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CancelObjectUseCaseTest {

    private final CancelObjectOutPort cancelObjectOutPort = mock(CancelObjectOutPort.class);

    private final CancelObjectUseCase cancelObjectUseCase = new CancelObjectUseCase(cancelObjectOutPort);

    @Test
    void cancelObject() {

        doNothing().when(cancelObjectOutPort).cancelObject(any(), any());

        cancelObjectUseCase.cancelObject("objectCoo", "user");

        verify(this.cancelObjectOutPort, times(1)).cancelObject("objectCoo", "user");
    }


}
