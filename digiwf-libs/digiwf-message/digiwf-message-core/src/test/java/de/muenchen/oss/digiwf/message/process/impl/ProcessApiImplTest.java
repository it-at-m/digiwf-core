package de.muenchen.oss.digiwf.message.process.impl;

import de.muenchen.oss.digiwf.message.core.api.MessageApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import de.muenchen.oss.digiwf.message.process.impl.dto.CorrelateMessageDto;
import de.muenchen.oss.digiwf.message.process.impl.dto.StartProcessDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Map;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ProcessApiImplTest {

    private final MessageApi messageApi = mock(MessageApi.class);

    private final ProcessApi processApi = new ProcessApiImpl(
            this.messageApi,
            "correlateMessageDestination",
            "startProcessDestination"
    );

    // dummy data
    private final String processDefinitionKey = "someProcessDefinitionKey";
    private final Map<String, Object> variables = Map.of("key", "value");
    private final String processInstanceId = "processInstanceId-123";


    @BeforeEach
    void setUp() {
        when(this.messageApi.sendMessage(any(), anyMap(), anyString())).thenReturn(true);
    }

    @Test
    void testStartProcess() {
        final boolean success = this.processApi.startProcess(this.processDefinitionKey, this.variables);
        assertThat(success).isTrue();
        this.verifyStartProcess(new StartProcessDto(this.processDefinitionKey, null, this.variables),
                "startProcessV01", "startProcessDestination");
    }

    @Test
    void testStartProcessWithFileContext() {
        final String fileContext = "someFileContext";
        final boolean success = this.processApi.startProcess(this.processDefinitionKey, this.variables, fileContext);
        assertThat(success).isTrue();
        this.verifyStartProcess(new StartProcessDto(this.processDefinitionKey, fileContext, this.variables),
                "startProcessV01", "startProcessDestination");
    }

    @Test
    void testCorrelateMessage() {
        final String messageName = "correlateMessage";
        final boolean success = this.processApi.correlateMessage(this.processInstanceId, messageName, this.variables);
        assertThat(success).isTrue();

        final ArgumentCaptor<CorrelateMessageDto> payloadCaptor = ArgumentCaptor.forClass(CorrelateMessageDto.class);
        final ArgumentCaptor<Map<String, Object>> headersCaptor = ArgumentCaptor.forClass(Map.class);
        final ArgumentCaptor<String> destinationCaptor = ArgumentCaptor.forClass(String.class);
        verify(this.messageApi).sendMessage(payloadCaptor.capture(), headersCaptor.capture(), destinationCaptor.capture());

        final CorrelateMessageDto payload = payloadCaptor.getValue();
        final Map<String, Object> headers = headersCaptor.getValue();

        assertThat(payload.getProcessInstanceId()).isEqualTo(this.processInstanceId);
        assertThat(payload.getMessageName()).isEqualTo(messageName);
        assertThat(payload.getPayloadVariables()).isEqualTo(this.variables);

        assertThat(headers)
            .hasSize(3)
            .containsEntry(TYPE, "correlatemessagev01")
            .containsEntry(DIGIWF_PROCESS_INSTANCE_ID, this.processInstanceId)
            .containsEntry(DIGIWF_MESSAGE_NAME, messageName);

        assertThat(destinationCaptor.getValue()).isEqualTo("correlateMessageDestination");
    }

    private void verifyStartProcess(final StartProcessDto payload, final String typeHeader, final String destination) {
        final ArgumentCaptor<StartProcessDto> payloadCaptor = ArgumentCaptor.forClass(StartProcessDto.class);
        final ArgumentCaptor<Map<String, Object>> headersCaptor = ArgumentCaptor.forClass(Map.class);
        final ArgumentCaptor<String> destinationCaptor = ArgumentCaptor.forClass(String.class);
        verify(this.messageApi).sendMessage(payloadCaptor.capture(), headersCaptor.capture(), destinationCaptor.capture());

        final Map<String, Object> headers = headersCaptor.getValue();

        assertThat(payloadCaptor.getValue()).isEqualTo(payload);

        assertThat(headers)
                .hasSize(1)
                .containsEntry(TYPE, "startProcessV01");

        assertThat(destinationCaptor.getValue()).isEqualTo(destination);
    }
}
