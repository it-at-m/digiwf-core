package de.muenchen.oss.digiwf.email.integration.infrastructure;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MonitoringServiceTest {

    private MeterRegistry meterRegistryMock;
    private Counter totalMailCounterMock;
    private Counter failureCounterMock;
    private MonitoringService monitoringService;

    @BeforeEach
    void setup() {
        meterRegistryMock = Mockito.mock(MeterRegistry.class);
        totalMailCounterMock = Mockito.mock(Counter.class);
        failureCounterMock = Mockito.mock(Counter.class);

        Mockito.when(meterRegistryMock.counter("totalMailCounterName")).thenReturn(totalMailCounterMock);
        Mockito.when(meterRegistryMock.counter("failureCounterName")).thenReturn(failureCounterMock);

        monitoringService = new MonitoringService(meterRegistryMock, "totalMailCounterName", "failureCounterName");
    }

    @Test
    public void sendMailSucceeded_shouldIncrementTotalMailCounter() {
        monitoringService.sendMailSucceeded();
        Mockito.verify(totalMailCounterMock).increment();
    }

    @Test
    public void sendMailFailed_shouldIncrementFailureCounter() {
        monitoringService.sendMailFailed();
        Mockito.verify(failureCounterMock).increment();
    }
}
