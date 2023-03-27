package io.muenchendigital.digiwf.message.process.impl;

import io.muenchendigital.digiwf.message.core.api.MessageApi;
import io.muenchendigital.digiwf.message.process.api.ProcessApi;
import io.muenchendigital.digiwf.message.process.impl.dto.BpmnErrorDto;
import io.muenchendigital.digiwf.message.process.impl.dto.CorrelateMessageDto;
import io.muenchendigital.digiwf.message.process.impl.dto.StartProcessDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Map;

import static io.muenchendigital.digiwf.message.common.MessageConstants.*;
import static org.mockito.Mockito.when;

class ProcessApiImplTest {

    private final MessageApi messageApi = Mockito.spy(Mockito.mock(MessageApi.class));

    private final ProcessApi processApi = new ProcessApiImpl(
            this.messageApi,
            "correlateMessageDestination",
            "startProcessDestination",
            "incidentMessageDestination",
            "technicalErrorMessageDestination"
    );

    // dummy data
    private final String processDefinitionKey = "someProcessDefinitionKey";
    private final Map<String, Object> variables = Map.of("key", "value");
    private final String processInstanceId = "processInstanceId-123";


    @BeforeEach
    void setUp() {
        when(this.messageApi.sendMessage(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
    }

    @Test
    void testStartProcess() {
        final boolean success = this.processApi.startProcess(this.processDefinitionKey, this.variables);
        Assertions.assertTrue(success);

        final ArgumentCaptor<Map<String, Object>> payloadCaptor = ArgumentCaptor.forClass(Map.class);
        final ArgumentCaptor<Map<String, Object>> headersCaptor = ArgumentCaptor.forClass(Map.class);
        final ArgumentCaptor<String> destinationCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(this.messageApi).sendMessage(payloadCaptor.capture(), headersCaptor.capture(), destinationCaptor.capture());

        final StartProcessDto payload = (StartProcessDto) payloadCaptor.getValue();
        Assertions.assertEquals(this.processDefinitionKey, payload.getKey());
        Assertions.assertEquals("startProcessV01", headersCaptor.getValue().get(TYPE));
        Assertions.assertEquals("startProcessDestination", destinationCaptor.getValue());
    }

    @Test
    void testStartProcessWithFileContext() {
        final String fileContext = "someFileContext";
        final boolean success = this.processApi.startProcess(this.processDefinitionKey, this.variables, fileContext);
        Assertions.assertTrue(success);

        final ArgumentCaptor<Map<String, Object>> payloadCaptor = ArgumentCaptor.forClass(Map.class);
        final ArgumentCaptor<Map<String, Object>> headersCaptor = ArgumentCaptor.forClass(Map.class);
        final ArgumentCaptor<String> destinationCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(this.messageApi).sendMessage(payloadCaptor.capture(), headersCaptor.capture(), destinationCaptor.capture());

        final StartProcessDto payload = (StartProcessDto) payloadCaptor.getValue();
        Assertions.assertEquals(this.processDefinitionKey, payload.getKey());
        Assertions.assertEquals(fileContext, payload.getFileContext());
        Assertions.assertEquals("startProcessV01", headersCaptor.getValue().get(TYPE));
        Assertions.assertEquals("startProcessDestination", destinationCaptor.getValue());
    }

    @Test
    void testCorrelateMessage() {
        final String messageName = "correlateMessage";
        final boolean success = this.processApi.correlateMessage(this.processInstanceId, messageName, this.variables);
        Assertions.assertTrue(success);

        final ArgumentCaptor<Map<String, Object>> payloadCaptor = ArgumentCaptor.forClass(Map.class);
        final ArgumentCaptor<Map<String, Object>> headersCaptor = ArgumentCaptor.forClass(Map.class);
        final ArgumentCaptor<String> destinationCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(this.messageApi).sendMessage(payloadCaptor.capture(), headersCaptor.capture(), destinationCaptor.capture());

        final CorrelateMessageDto payload = (CorrelateMessageDto) payloadCaptor.getValue();
        Assertions.assertEquals(this.processInstanceId, payload.getProcessInstanceId());
        Assertions.assertEquals(messageName, payload.getMessageName());
        Assertions.assertEquals("correlatemessagev01", headersCaptor.getValue().get(TYPE));
        Assertions.assertEquals(this.processInstanceId, headersCaptor.getValue().get(DIGIWF_PROCESS_INSTANCE_ID));
        Assertions.assertEquals(messageName, headersCaptor.getValue().get(DIGIWF_MESSAGE_NAME));
        Assertions.assertEquals("correlateMessageDestination", destinationCaptor.getValue());
    }

    @Test
    void testIncidentMessage() {
        final String messageName = "anyMessageName";
        final String errorMessage = "someErrorMessage";
        final boolean success = this.processApi.handleIncident(this.processInstanceId, messageName, errorMessage);
        Assertions.assertTrue(success);

        final ArgumentCaptor<String> payloadCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<Map<String, Object>> headersCaptor = ArgumentCaptor.forClass(Map.class);
        final ArgumentCaptor<String> destinationCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(this.messageApi).sendMessage(payloadCaptor.capture(), headersCaptor.capture(), destinationCaptor.capture());

        Assertions.assertEquals(errorMessage, payloadCaptor.getValue());
        Assertions.assertEquals("incidentMessageDestination", headersCaptor.getValue().get(TYPE));
        Assertions.assertEquals(this.processInstanceId, headersCaptor.getValue().get(DIGIWF_PROCESS_INSTANCE_ID));
        Assertions.assertEquals(messageName, headersCaptor.getValue().get(DIGIWF_MESSAGE_NAME));
        Assertions.assertEquals("incidentMessageDestination", destinationCaptor.getValue());
    }

    @Test
    void testTechnicalErrorMessage() {
        final String errorCode = "400";
        final String errorMessage = "someErrorMessage";
        final boolean success = this.processApi.handleBpmnError(this.processInstanceId, errorCode, errorMessage);
        Assertions.assertTrue(success);

        final ArgumentCaptor<BpmnErrorDto> payloadCaptor = ArgumentCaptor.forClass(BpmnErrorDto.class);
        final ArgumentCaptor<Map<String, Object>> headersCaptor = ArgumentCaptor.forClass(Map.class);
        final ArgumentCaptor<String> destinationCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(this.messageApi).sendMessage(payloadCaptor.capture(), headersCaptor.capture(), destinationCaptor.capture());

        Assertions.assertEquals(errorMessage, payloadCaptor.getValue().getErrorMessage());
        Assertions.assertEquals(errorCode, payloadCaptor.getValue().getErrorCode());
        Assertions.assertEquals(this.processInstanceId, payloadCaptor.getValue().getProcessInstanceId());
        Assertions.assertEquals("technicalErrorMessageDestination", headersCaptor.getValue().get(TYPE));
        Assertions.assertEquals("technicalErrorMessageDestination", destinationCaptor.getValue());
    }

}
