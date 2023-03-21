package io.muenchendigital.digiwf.message.process.impl;

import io.muenchendigital.digiwf.message.process.impl.dto.BpmnErrorDto;
import io.muenchendigital.digiwf.message.process.impl.dto.CorrelateMessageDto;
import io.muenchendigital.digiwf.message.process.impl.dto.StartProcessDto;
import io.muenchendigital.digiwf.message.process.impl.model.Message;
import io.muenchendigital.digiwf.util.DummyMessageApi;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.util.Map;

class ProcessPortImplTest {

    @Spy
    private final DummyMessageApi messageApi = Mockito.spy(new DummyMessageApi());

    private final ProcessPortImpl processPort = new ProcessPortImpl(this.messageApi);

    @Test
    void testStartProcess() {
        final Message<StartProcessDto> message = new Message<>();
        message.addPayload(StartProcessDto.builder()
                .data(Map.of("key", "value"))
                .key("theProcessKey")
                .build());
        message.addHeader("key", "value");

        final boolean success = this.processPort.startProcess(message, "startProcessDestination");
        Assertions.assertTrue(success);

        final ArgumentCaptor<Object> payloadCaptor = ArgumentCaptor.forClass(Object.class);
        final ArgumentCaptor<Map<String, Object>> headersCaptor = ArgumentCaptor.forClass(Map.class);
        Mockito.verify(this.messageApi).sendMessage(payloadCaptor.capture(), headersCaptor.capture(), Mockito.eq("startProcessDestination"));

        Assertions.assertEquals(payloadCaptor.getValue(), message.getPayload());
        Assertions.assertEquals(headersCaptor.getValue(), message.getHeaders());
    }

    @Test
    void testSendCorrelateMessage() {
        final Message<CorrelateMessageDto> message = new Message<>();
        message.addPayload(CorrelateMessageDto.builder()
                .processInstanceId("processInstanceId")
                .messageName("messageName")
                .businessKey("businessKey")
                .payloadVariables(Map.of("key", "value"))
                .build());
        message.addHeader("key", "value");

        final boolean success = this.processPort.sendCorrelateMessage(message, "sendCorrelateMessageDestination");
        Assertions.assertTrue(success);

        final ArgumentCaptor<Object> payloadCaptor = ArgumentCaptor.forClass(Object.class);
        final ArgumentCaptor<Map<String, Object>> headersCaptor = ArgumentCaptor.forClass(Map.class);
        Mockito.verify(this.messageApi).sendMessage(payloadCaptor.capture(), headersCaptor.capture(), Mockito.eq("sendCorrelateMessageDestination"));

        Assertions.assertEquals(payloadCaptor.getValue(), message.getPayload());
        Assertions.assertEquals(headersCaptor.getValue(), message.getHeaders());
    }

    @Test
    void testSendIncidentMessage() {
        final Message<Object> message = new Message<>();
        message.addPayload("someErrorMessage");
        message.addHeader("key", "value");

        final boolean success = this.processPort.sendIncidentMessage(message, "sendIncidentMessageDestination");
        Assertions.assertTrue(success);

        final ArgumentCaptor<Object> payloadCaptor = ArgumentCaptor.forClass(Object.class);
        final ArgumentCaptor<Map<String, Object>> headersCaptor = ArgumentCaptor.forClass(Map.class);
        Mockito.verify(this.messageApi).sendMessage(payloadCaptor.capture(), headersCaptor.capture(), Mockito.eq("sendIncidentMessageDestination"));

        Assertions.assertEquals(payloadCaptor.getValue(), message.getPayload());
        Assertions.assertEquals(headersCaptor.getValue(), message.getHeaders());
    }

    @Test
    void testSendTechnicalError() {
        final Message<BpmnErrorDto> message = new Message<>();
        message.addPayload(BpmnErrorDto.builder()
                .processInstanceId("processInstanceId")
                .errorMessage("errorMessage")
                .build());
        message.addHeader("key", "value");

        final boolean success = this.processPort.sendBpmnError(message, "sendTechnicalErrorMessageDestination");
        Assertions.assertTrue(success);

        final ArgumentCaptor<Object> payloadCaptor = ArgumentCaptor.forClass(Object.class);
        final ArgumentCaptor<Map<String, Object>> headersCaptor = ArgumentCaptor.forClass(Map.class);
        Mockito.verify(this.messageApi).sendMessage(payloadCaptor.capture(), headersCaptor.capture(), Mockito.eq("sendTechnicalErrorMessageDestination"));

        Assertions.assertEquals(payloadCaptor.getValue(), message.getPayload());
        Assertions.assertEquals(headersCaptor.getValue(), message.getHeaders());
    }

}
