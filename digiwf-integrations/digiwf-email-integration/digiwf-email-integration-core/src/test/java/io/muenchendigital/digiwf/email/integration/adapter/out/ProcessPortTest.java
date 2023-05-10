package io.muenchendigital.digiwf.email.integration.adapter.out;

import io.muenchendigital.digiwf.message.process.api.ProcessApi;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ProcessPortTest {

    @Test
    public void testCorrelateMessage() {
        final ProcessApi processApiMock = Mockito.mock(ProcessApi.class);

        final ProcessPort processPort = new ProcessPort(processApiMock);

        final String processInstanceId = "exampleProcessInstanceId";
        final String messageName = "exampleMessageName";
        final Map<String, Object> message = new HashMap<>();

        processPort.correlateMessage(processInstanceId, messageName, message);

        verify(processApiMock, times(1)).correlateMessage(processInstanceId, messageName, message);
    }
}
