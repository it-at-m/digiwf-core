package io.muenchendigital.digiwf.email.integration.adapter.out;

import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ProcessAdapterTest {

    @Test
    public void testCorrelateMessage() {
        final ProcessApi processApiMock = Mockito.mock(ProcessApi.class);

        final ProcessAdapter processAdapter = new ProcessAdapter(processApiMock);

        final String processInstanceId = "exampleProcessInstanceId";
        final String messageName = "exampleMessageName";
        final Map<String, Object> message = new HashMap<>();

        processAdapter.correlateMessage(processInstanceId, messageName, message);

        verify(processApiMock, times(1)).correlateMessage(processInstanceId, messageName, message);
    }
}
