package de.muenchen.oss.digiwf.cosys.integration.adapter.out;

import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;


class ProcessAdapterTest {

    @Test
    void correlateMessage() {
        final ProcessApi processApi = mock(ProcessApi.class);

        final ProcessAdapter processAdapter = new ProcessAdapter(processApi);

        final String processInstanceId = "processInstanceId";
        final String integrationName = "cosysIntegration";
        final Map<String, Object> message = new HashMap<>();

        processAdapter.correlateMessage(processInstanceId,integrationName,message);

        verify(processApi).correlateMessage(processInstanceId,integrationName,message);
        verifyNoMoreInteractions(processApi);
    }
}
