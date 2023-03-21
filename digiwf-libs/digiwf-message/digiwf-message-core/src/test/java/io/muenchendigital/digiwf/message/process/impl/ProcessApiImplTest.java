package io.muenchendigital.digiwf.message.process.impl;

import io.muenchendigital.digiwf.message.process.impl.dto.BpmnErrorDto;
import io.muenchendigital.digiwf.message.process.impl.dto.CorrelateMessageDto;
import io.muenchendigital.digiwf.message.process.impl.dto.StartProcessDto;
import io.muenchendigital.digiwf.message.process.impl.model.Message;
import io.muenchendigital.digiwf.util.DummyProcessPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.util.Map;

import static io.muenchendigital.digiwf.message.common.MessageConstants.*;

class ProcessApiImplTest {

    @Spy
    private DummyProcessPort dummyProcessPort = Mockito.spy(new DummyProcessPort());
    private final ProcessApiImpl processApi = new ProcessApiImpl(
            "correlateMessageDestination",
            "startProcessDestination",
            "incidentMessageDestination",
            "technicalErrorMessageDestination",
            this.dummyProcessPort,
            this.dummyProcessPort,
            this.dummyProcessPort,
            this.dummyProcessPort
    );

    // dummy data
    private final String processDefinitionKey = "someProcessDefinitionKey";
    private final Map<String, Object> variables = Map.of("key", "value");
    private final String processInstanceId = "processInstanceId-123";


    @Test
    void testStartProcess() {
        final boolean success = this.processApi.startProcess(this.processDefinitionKey, this.variables);
        Assertions.assertTrue(success);

        final ArgumentCaptor<Message<StartProcessDto>> messageCaptor = ArgumentCaptor.forClass(Message.class);
        Mockito.verify(this.dummyProcessPort).startProcess(messageCaptor.capture(), Mockito.eq("startProcessDestination"));

        Assertions.assertEquals(messageCaptor.getValue().getPayload().getKey(), this.processDefinitionKey);
        Assertions.assertEquals(messageCaptor.getValue().getPayload().getData(), this.variables);
        Assertions.assertNull(messageCaptor.getValue().getPayload().getFileContext());

        Assertions.assertTrue(messageCaptor.getValue().getHeaders().containsKey("type"));
    }

    @Test
    void testStartProcessWithFileContext() {
        final String fileContext = "someFileContext";
        final boolean success = this.processApi.startProcess(this.processDefinitionKey, this.variables, fileContext);
        Assertions.assertTrue(success);

        final ArgumentCaptor<Message<StartProcessDto>> messageCaptor = ArgumentCaptor.forClass(Message.class);
        Mockito.verify(this.dummyProcessPort).startProcess(messageCaptor.capture(), Mockito.eq("startProcessDestination"));

        Assertions.assertEquals(messageCaptor.getValue().getPayload().getKey(), this.processDefinitionKey);
        Assertions.assertEquals(messageCaptor.getValue().getPayload().getData(), this.variables);
        Assertions.assertEquals(messageCaptor.getValue().getPayload().getFileContext(), fileContext);

        Assertions.assertTrue(messageCaptor.getValue().getHeaders().containsKey("type"));
    }

    @Test
    void testCorrelateMessage() {
        final String messageName = "correlateMessage";
        final boolean success = this.processApi.correlateMessage(this.processInstanceId, messageName, this.variables);
        Assertions.assertTrue(success);

        final ArgumentCaptor<Message<CorrelateMessageDto>> messageCaptor = ArgumentCaptor.forClass(Message.class);
        Mockito.verify(this.dummyProcessPort).sendCorrelateMessage(messageCaptor.capture(), Mockito.eq("correlateMessageDestination"));

        Assertions.assertEquals(messageCaptor.getValue().getPayload().getMessageName(), messageName);
        Assertions.assertEquals(messageCaptor.getValue().getPayload().getPayloadVariables(), this.variables);
        Assertions.assertEquals(messageCaptor.getValue().getPayload().getProcessInstanceId(), this.processInstanceId);

        Assertions.assertEquals(messageCaptor.getValue().getHeaders().get(TYPE), "correlatemessagev01");
        Assertions.assertEquals(messageCaptor.getValue().getHeaders().get(DIGIWF_PROCESS_INSTANCE_ID), this.processInstanceId);
        Assertions.assertEquals(messageCaptor.getValue().getHeaders().get(DIGIWF_MESSAGE_NAME), messageName);
    }

    @Test
    void testIncidentMessage() {
        final String messageName = "anyMessageName";
        final String errorMessage = "someErrorMessage";
        final boolean success = this.processApi.handleIncident(this.processInstanceId, messageName, errorMessage);
        Assertions.assertTrue(success);

        final ArgumentCaptor<Message<Object>> messageCaptor = ArgumentCaptor.forClass(Message.class);
        Mockito.verify(this.dummyProcessPort).sendIncidentMessage(messageCaptor.capture(), Mockito.eq("incidentMessageDestination"));

        Assertions.assertEquals(messageCaptor.getValue().getPayload(), errorMessage);

        Assertions.assertEquals(messageCaptor.getValue().getHeaders().get(TYPE), "incidentMessageDestination");
        Assertions.assertEquals(messageCaptor.getValue().getHeaders().get(DIGIWF_PROCESS_INSTANCE_ID), this.processInstanceId);
        Assertions.assertEquals(messageCaptor.getValue().getHeaders().get(DIGIWF_MESSAGE_NAME), messageName);
    }

    @Test
    void testTechnicalErrorMessage() {
        final String errorCode = "400";
        final String errorMessage = "someErrorMessage";
        final boolean success = this.processApi.handleBpmnError(this.processInstanceId, errorCode, errorMessage);
        Assertions.assertTrue(success);

        final ArgumentCaptor<Message<BpmnErrorDto>> messageCaptor = ArgumentCaptor.forClass(Message.class);
        Mockito.verify(this.dummyProcessPort).sendBpmnError(messageCaptor.capture(), Mockito.eq("technicalErrorMessageDestination"));

        Assertions.assertEquals(messageCaptor.getValue().getPayload().getErrorMessage(), errorMessage);
        Assertions.assertEquals(messageCaptor.getValue().getPayload().getErrorCode(), errorCode);
        Assertions.assertEquals(messageCaptor.getValue().getPayload().getProcessInstanceId(), this.processInstanceId);

        Assertions.assertEquals(messageCaptor.getValue().getHeaders().get(TYPE), "technicalErrorMessageDestination");
    }

}
