package de.muenchen.oss.digiwf.dms.integration.adapter.in;

import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import javax.validation.ValidationException;
import java.util.Map;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.DIGIWF_PROCESS_INSTANCE_ID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class CancelObjectMessageProcessorTest extends MessageProcessorTestBase {

    private final CancelObjectDto cancelObjectDto = new CancelObjectDto(
            "objectCoo",
            "user"
    );
    private Message<CancelObjectDto> message;

    @BeforeEach
    void setup() {
        setupBase();
        Mockito.doNothing().when(cancelObjectUseCaseMock).cancelObject(
                cancelObjectDto.getObjectCoo(),
                cancelObjectDto.getUser());
        this.message = new Message<>() {
            @Override
            public CancelObjectDto getPayload() {
                return cancelObjectDto;
            }

            @Override
            public MessageHeaders getHeaders() {
                return messageHeaders;
            }
        };
    }

    @Test
    void testCancelObjectSuccessful() {
        messageProcessor.cancelObject().accept(this.message);
        verify(cancelObjectUseCaseMock, times(1)).cancelObject(cancelObjectDto.getObjectCoo(), cancelObjectDto.getUser());
    }

    @Test
    void testCancelObjectValidationException() {
        Mockito.doThrow(new ValidationException("Test ValidationException")).when(cancelObjectUseCaseMock).cancelObject(any(), any());
        messageProcessor.cancelObject().accept(this.message);
        final ArgumentCaptor<Map> messageHeaderArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        verify(errorApiMock, times(1)).handleIncident(messageHeaderArgumentCaptor.capture(), any(IncidentError.class));
        Assertions.assertTrue(messageHeaderArgumentCaptor.getValue().containsKey(DIGIWF_PROCESS_INSTANCE_ID));
    }


    @Test
    void testCancelObjectIncidentError() {
        Mockito.doThrow(new IncidentError("Error Message")).when(cancelObjectUseCaseMock).cancelObject(any(), any());
        messageProcessor.cancelObject().accept(this.message);
        final ArgumentCaptor<Map> messageHeaderArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        verify(errorApiMock, times(1)).handleIncident(messageHeaderArgumentCaptor.capture(), any(IncidentError.class));
        Assertions.assertTrue(messageHeaderArgumentCaptor.getValue().containsKey(DIGIWF_PROCESS_INSTANCE_ID));
    }
}

