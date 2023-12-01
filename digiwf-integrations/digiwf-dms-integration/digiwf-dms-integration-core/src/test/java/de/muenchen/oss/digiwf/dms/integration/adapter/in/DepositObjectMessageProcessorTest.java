package de.muenchen.oss.digiwf.dms.integration.adapter.in;

import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import jakarta.validation.ValidationException;
import java.util.Map;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.DIGIWF_PROCESS_INSTANCE_ID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class DepositObjectMessageProcessorTest extends MessageProcessorTestBase {

    private final DepositObjectDto depositObjectDto = new DepositObjectDto(
            "objectCoo",
            "user"
    );
    private Message<DepositObjectDto> message;

    @BeforeEach
    void setup() {
        setupBase();
        Mockito.doNothing().when(depositObjectUseCaseMock).depositObject(
                depositObjectDto.getObjectCoo(),
                depositObjectDto.getUser());
        this.message = new Message<>() {
            @Override
            public DepositObjectDto getPayload() {
                return depositObjectDto;
            }

            @Override
            public MessageHeaders getHeaders() {
                return messageHeaders;
            }
        };
    }

    @Test
    void testDepositObjectSuccessful() {
        messageProcessor.depositObject().accept(this.message);
        verify(depositObjectUseCaseMock, times(1)).depositObject(depositObjectDto.getObjectCoo(), depositObjectDto.getUser());
    }

    @Test
    void testDepositObjectValidationException() {
        Mockito.doThrow(new ValidationException("Test ValidationException")).when(depositObjectUseCaseMock).depositObject(any(), any());
        messageProcessor.depositObject().accept(this.message);
        final ArgumentCaptor<Map> messageHeaderArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        verify(errorApiMock, times(1)).handleIncident(messageHeaderArgumentCaptor.capture(), any(IncidentError.class));
        Assertions.assertTrue(messageHeaderArgumentCaptor.getValue().containsKey(DIGIWF_PROCESS_INSTANCE_ID));
    }


    @Test
    void testDepositObjectIncidentError() {
        Mockito.doThrow(new IncidentError("Error Message")).when(depositObjectUseCaseMock).depositObject(any(), any());
        messageProcessor.depositObject().accept(this.message);
        final ArgumentCaptor<Map> messageHeaderArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        verify(errorApiMock, times(1)).handleIncident(messageHeaderArgumentCaptor.capture(), any(IncidentError.class));
        Assertions.assertTrue(messageHeaderArgumentCaptor.getValue().containsKey(DIGIWF_PROCESS_INSTANCE_ID));
    }
}

