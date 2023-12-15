package de.muenchen.oss.digiwf.connector.core.adapter.in.streaming;

import de.muenchen.oss.digiwf.connector.core.application.port.in.CreateBpmnErrorInPort;
import de.muenchen.oss.digiwf.connector.core.domain.BpmnError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.messaging.Message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BpmnErrorEventConsumerTest {

    private BpmnErrorEventConsumer consumer;
    private CreateBpmnErrorInPort inPort;

    @BeforeEach
    void setUp() {
        inPort = mock(CreateBpmnErrorInPort.class);
        consumer = new BpmnErrorEventConsumer(inPort);
    }

    @Test
    void createBpmnError_shouldProcessBpmnErrorDtoAndCallInPort() {
        // given
        BpmnErrorDto dto = new BpmnErrorDto();
        dto.setProcessInstanceId("123");
        dto.setMessageName("testMessage");
        dto.setErrorCode("404");
        dto.setErrorMessage("Not found");

        Message<BpmnErrorDto> message = mock(Message.class);
        when(message.getPayload()).thenReturn(dto);

        // when
        consumer.createBpmnError().accept(message);

        // then
        ArgumentCaptor<BpmnError> captor = ArgumentCaptor.forClass(BpmnError.class);
        verify(inPort).createBpmnError(captor.capture());
        BpmnError capturedBpmnError = captor.getValue();

        assertEquals("123", capturedBpmnError.getProcessInstanceId());
        assertEquals("testMessage", capturedBpmnError.getMessageName());
        assertEquals("404", capturedBpmnError.getErrorCode());
        assertEquals("Not found", capturedBpmnError.getErrorMessage());
    }
}
