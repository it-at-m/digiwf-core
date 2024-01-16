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
        final String type = "type";
        final Map<String, Object> message = new HashMap<>();

        processAdapter.correlateMessage(processInstanceId,type,integrationName,message);

        verify(processApi).correlateMessage(processInstanceId,type,integrationName,message);
        verifyNoMoreInteractions(processApi);
    }
}
