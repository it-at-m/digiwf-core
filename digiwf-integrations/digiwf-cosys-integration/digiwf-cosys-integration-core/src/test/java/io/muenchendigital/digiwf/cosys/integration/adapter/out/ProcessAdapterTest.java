package io.muenchendigital.digiwf.cosys.integration.adapter.out;

import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;


class ProcessAdapterTest {

    @Test
    void correlateMessage() {
        final ProcessApi processApi = mock(ProcessApi.class);

        final ProcessAdapter processAdapter = new ProcessAdapter(processApi);

        final String processInstanceId = "processInstanceId";
        final String messageName = "messageName";
        final Map<String, Object> message = new HashMap<>();

        processAdapter.correlateMessage(processInstanceId,messageName,message);

        verify(processApi).correlateMessage(processInstanceId,messageName,message);
        verifyNoMoreInteractions(processApi);
    }
}
