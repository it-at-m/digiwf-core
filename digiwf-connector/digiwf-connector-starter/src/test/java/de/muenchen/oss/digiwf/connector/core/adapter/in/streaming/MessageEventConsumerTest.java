package de.muenchen.oss.digiwf.connector.core.adapter.in.streaming;


import de.muenchen.oss.digiwf.connector.core.application.port.in.CorrelateMessageInPort;
import de.muenchen.oss.digiwf.connector.core.domain.MessageCorrelation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.messaging.Message;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MessageEventConsumerTest {

    private MessageEventConsumer consumer;
    private CorrelateMessageInPort inPort;

    @BeforeEach
    void setUp() {
        inPort = mock(CorrelateMessageInPort.class);
        consumer = new MessageEventConsumer(inPort);
    }

    @Test
    void correlateMessage_shouldProcessCorrelateMessageEventAndCallInPort() {
        // given
        CorrelateMessageDto event = new CorrelateMessageDto();
        event.setProcessInstanceId("123");
        event.setMessageName("testMessage");
        event.setBusinessKey("businessKey123");
        event.setPayloadVariables(Map.of("key1", "value1"));
        event.setPayloadVariablesLocal(Map.of("localKey1", "localValue1"));

        Message<CorrelateMessageDto> message = mock(Message.class);
        when(message.getPayload()).thenReturn(event);

        // when
        consumer.correlateMessage().accept(message);

        // then
        ArgumentCaptor<MessageCorrelation> captor = ArgumentCaptor.forClass(MessageCorrelation.class);
        verify(inPort).correlateMessage(captor.capture());
        MessageCorrelation capturedMessageCorrelation = captor.getValue();

        assertEquals("123", capturedMessageCorrelation.getProcessInstanceId());
        assertEquals("testMessage", capturedMessageCorrelation.getMessageName());
        assertEquals("businessKey123", capturedMessageCorrelation.getBusinessKey());
        assertEquals(Map.of("key1", "value1"), capturedMessageCorrelation.getPayloadVariables());
        assertEquals(Map.of("localKey1", "localValue1"), capturedMessageCorrelation.getPayloadVariablesLocal());
    }
}
