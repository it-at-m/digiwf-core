package de.muenchen.oss.digiwf.dms.integration.adapter.in;

import de.muenchen.oss.digiwf.dms.integration.domain.Procedure;
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

class CreateProcedureMessageProcessorTest extends MessageProcessorTestBase {

    private final CreateProcedureDto createProcedureDto = new CreateProcedureDto(
            "sachakteCoo",
            "title",
            "user"
    );
    private Message<CreateProcedureDto> message;

    @BeforeEach
    void setup() {
        setupBase();
        Mockito.when(createProcedureMock.createProcedure(
                        createProcedureDto.getTitle(),
                        createProcedureDto.getFileCOO(),
                        createProcedureDto.getUser()))
                .thenReturn(new Procedure("coo", createProcedureDto.getTitle(), createProcedureDto.getFileCOO()));
        this.message = new Message<>() {
            @Override
            public CreateProcedureDto getPayload() {
                return createProcedureDto;
            }

            @Override
            public MessageHeaders getHeaders() {
                return messageHeaders;
            }
        };
    }

    @Test
    void testDmsIntegrationCreateProcedureSuccessfully() {
        messageProcessor.createProcedure().accept(this.message);
        verify(createProcedureMock, times(1)).createProcedure(createProcedureDto.getTitle(), createProcedureDto.getFileCOO(), createProcedureDto.getUser());
    }

    @Test
    void testDmsIntegrationHandlesValidationException() {
        Mockito.doThrow(new ValidationException("Test ValidationException")).when(createProcedureMock).createProcedure(any(), any(), any());
        messageProcessor.createProcedure().accept(this.message);
        final ArgumentCaptor<Map> messageHeaderArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        verify(errorApiMock, times(1)).handleIncident(messageHeaderArgumentCaptor.capture(), any(IncidentError.class));
        Assertions.assertTrue(messageHeaderArgumentCaptor.getValue().containsKey(DIGIWF_PROCESS_INSTANCE_ID));
    }


    @Test
    void testDmsIntegrationHandlesIncidentError() {
        Mockito.doThrow(new IncidentError("Error Message")).when(createProcedureMock).createProcedure(any(), any(), any());
        messageProcessor.createProcedure().accept(this.message);
        final ArgumentCaptor<Map> messageHeaderArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        verify(errorApiMock, times(1)).handleIncident(messageHeaderArgumentCaptor.capture(), any(IncidentError.class));
        Assertions.assertTrue(messageHeaderArgumentCaptor.getValue().containsKey(DIGIWF_PROCESS_INSTANCE_ID));
    }
}

