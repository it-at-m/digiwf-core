package de.muenchen.oss.digiwf.dms.integration.adapter.in;

import de.muenchen.oss.digiwf.dms.integration.application.port.in.CreateVorgangUseCase;
import de.muenchen.oss.digiwf.dms.integration.domain.Procedure;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
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

import static de.muenchen.oss.digiwf.message.common.MessageConstants.DIGIWF_MESSAGE_NAME;
import static de.muenchen.oss.digiwf.message.common.MessageConstants.DIGIWF_PROCESS_INSTANCE_ID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class MessageProcessorTest {
    private final ErrorApi errorApiMock = Mockito.mock(ErrorApi.class);
    private final ProcessApi processApi = Mockito.mock(ProcessApi.class);
    private final CreateVorgangUseCase sendMailMock = Mockito.mock(CreateVorgangUseCase.class);
    private final String processInstanceId = "exampleProcessInstanceId";
    private final MessageHeaders messageHeaders = new MessageHeaders(Map.of(DIGIWF_PROCESS_INSTANCE_ID, this.processInstanceId, DIGIWF_MESSAGE_NAME, "messageName"));
    private final CreateVorgangDto createVorgangDto = new CreateVorgangDto(
            "sachakteCoo",
            "title",
            "user"
    );
    private MessageProcessor messageProcessor;
    private Message<CreateVorgangDto> message;

    @BeforeEach
    void setup() {
        this.messageProcessor = new MessageProcessor(processApi, errorApiMock, sendMailMock);
        Mockito.when(sendMailMock.createVorgang(
                        createVorgangDto.getTitle(),
                        createVorgangDto.getFileCOO(),
                        createVorgangDto.getUser()))
                .thenReturn(new Procedure("coo", createVorgangDto.getTitle(), createVorgangDto.getFileCOO()));
        this.message = new Message<CreateVorgangDto>() {
            @Override
            public CreateVorgangDto getPayload() {
                return createVorgangDto;
            }

            @Override
            public MessageHeaders getHeaders() {
                return messageHeaders;
            }
        };
    }

    @Test
    void testDmsIntegrationCreateVorgangSuccessfully() {
        messageProcessor.createVorgang().accept(this.message);
        verify(sendMailMock, times(1)).createVorgang(createVorgangDto.getTitle(), createVorgangDto.getFileCOO(), createVorgangDto.getUser());
    }

    @Test
    void testDmsIntegrationHandlesValidationException() {
        Mockito.doThrow(new ValidationException("Test ValidationException")).when(sendMailMock).createVorgang(any(), any(), any());
        messageProcessor.createVorgang().accept(this.message);
        final ArgumentCaptor<Map> messageHeaderArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        verify(errorApiMock, times(1)).handleIncident(messageHeaderArgumentCaptor.capture(), any(IncidentError.class));
        Assertions.assertTrue(messageHeaderArgumentCaptor.getValue().containsKey(DIGIWF_PROCESS_INSTANCE_ID));
    }


    @Test
    void testDmsIntegrationHandlesIncidentError() {
        Mockito.doThrow(new IncidentError("Error Message")).when(sendMailMock).createVorgang(any(), any(), any());
        messageProcessor.createVorgang().accept(this.message);
        final ArgumentCaptor<Map> messageHeaderArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        verify(errorApiMock, times(1)).handleIncident(messageHeaderArgumentCaptor.capture(), any(IncidentError.class));
        Assertions.assertTrue(messageHeaderArgumentCaptor.getValue().containsKey(DIGIWF_PROCESS_INSTANCE_ID));
    }
}

